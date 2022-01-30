package com.jg.pochi.service.impl;

import com.jg.pochi.common.Page;
import com.jg.pochi.constant.CoreConstant;
import com.jg.pochi.enums.ResultEnums;
import com.jg.pochi.enums.StateEnums;
import com.jg.pochi.exception.PochiException;
import com.jg.pochi.mapper.SysMenuMapper;
import com.jg.pochi.pojo.SysMenu;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.vo.RouterVo;
import com.jg.pochi.pojo.vo.SysMenuVo;
import com.jg.pochi.service.SysMenuService;
import com.jg.pochi.utils.ShiroUtils;
import com.jg.pochi.utils.StringUtils;
import org.apache.logging.log4j.core.Core;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author Peekaboo
 * Date 2021/12/15 11:45
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 添加菜单
     * @param sysMenu
     */
    @Override
    public void save(SysMenu sysMenu) {
        //初始化父级菜单ID默认值
        if(sysMenu.getMenuId() == null ){
            sysMenu.setMenuId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //在同一父菜单下查询菜单名相同的菜单信息
        SysMenu menu =  sysMenuMapper.getParentIdAndName(sysMenu);
        //如果返回结果menu不为空,说明存在同名子菜单
        if (menu != null){
            throw new PochiException(ResultEnums.MENU_EXISTS);
        }
        //如果菜单不存在，入库表
        SysUser loginUser = ShiroUtils.getLoginUser();
        sysMenu.setCreateBy(loginUser.getUsername());
        sysMenu.setUpdateBy(loginUser.getUsername());
        sysMenuMapper.save(sysMenu);
    }


    @Override
    public void update(SysMenu sysMenu) {
        if (sysMenu.getParentId() == null){
            sysMenu.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //在同一父菜单下查询菜单名相同的菜单信息
        SysMenu menu = sysMenuMapper.getParentIdAndName(sysMenu);
        if (menu!=null && !menu.getMenuId().equals(sysMenu.getMenuId())){
            //如果菜单存在并且编号不相同，说明已有同名菜单存在
            throw new PochiException(ResultEnums.MENU_EXISTS);
        }

        SysUser loginUser = ShiroUtils.getLoginUser();
        sysMenu.setUpdateBy(loginUser.getUsername());
        sysMenuMapper.update(sysMenu);
    }

    /**
     * 根据ID删除菜单信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysMenuMapper.deleteById(id);
    }

    /**
     * 根据id查询
     * @param id
     */
    @Override
    public SysMenu get(Long id) {
        return sysMenuMapper.getById(id);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<SysMenu> getByPage(Page<SysMenu> page) {
        // 设置默认的当前页和每页条数
        Integer pageNumber = page.getPageNumber();
        if(pageNumber == null && pageNumber < 1){
            pageNumber = 1;
            page.setPageNumber(pageNumber);
        }
        //查询总条数
        Integer totalCount = sysMenuMapper.countByPage(page);
        List<SysMenu> list = sysMenuMapper.getByPage(page);
        page.setList(list);
        //这里存进了总条数同时也存进了总页数,然后一起放进page
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 构造菜单树形结构数据
     * @return
     */
    @Override
    public List<SysMenuVo> getTreeList() {
        // 查询出所有的菜单
        List<SysMenu> menuList = sysMenuMapper.getAll();

        // 过滤出所有顶级菜单
        return menuList.stream().filter(e->e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                // 只要父级菜单是0的就是顶级菜单
        .map(e->{
            // 将顶级菜单转换成我们的视图类
            SysMenuVo sysMenuVo = new SysMenuVo();
            BeanUtils.copyProperties(e,sysMenuVo);
            return sysMenuVo;
        })
        .map(e->{
            // SysMenuVo类中有一个子菜单List<SysMenuVo>格式
            // 这样就能配合根据顶级菜单的ID，递归从剩余的列表中找子菜单
           e.setChildren(getChildren(e,menuList));
           //如果发现子节点为空，则设为null
           if (CollectionUtils.isEmpty(e.getChildren())){
               e.setChildren(null);
           }
            return e;
        })

        .collect(Collectors.toList());
    }

    /**
     * 封装方法_递归构造树形菜单
     * @param sysMenuVo 当前菜单
     * @param menuList 原始菜单数据List<SysMenu>
     * @return
     */
    private List<SysMenuVo> getChildren(SysMenuVo sysMenuVo, List<SysMenu> menuList) {
        // 第一步，遍历menuList,只要当前迭代元素的ParentId是当前sysMenuVo的菜单ID,则过滤出来。
        List<SysMenuVo> childrenList = menuList.stream().filter(e -> e.getParentId() == sysMenuVo.getMenuId())
                // 第二步，把子菜单每一项转成SysMenuVo
                .map(e -> {
                    SysMenuVo sysMenuVoNew = new SysMenuVo();
                    BeanUtils.copyProperties(e, sysMenuVoNew);
                    return sysMenuVoNew;
                })
                // 第三步，递归找到本次获取到的所有子菜单的子菜单
                .map(e -> {
                    e.setChildren(getChildren(e, menuList));
                    //子节点则继续
                    if (CollectionUtils.isEmpty(e.getChildren())) {
                        e.setChildren(null);
                    }
                    return e;
                }).collect(Collectors.toList());
        // 这一步过滤掉空的子节点集合
        if(CollectionUtils.isEmpty(childrenList)){
            return null;
        }
        return childrenList;
    }



    /**
     * 根据角色查询菜单，其中不包括父级菜单
     */
    @Override
    public List<Long> getRoleSelectMenu(Long roleId) {
        List<SysMenu> menuList = sysMenuMapper.getRoleSelectMenu(roleId);
        List<Long> collect = menuList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取动态路由
     * @return
     */
    @Override
    public List<RouterVo> getRouters() {
        // 1. 查询出当前登录用户所拥有的启用中的所有菜单（权限不要查）
        SysUser loginUser = ShiroUtils.getLoginUser();
        List<SysMenu> menuList =  sysMenuMapper.getEnableMenuByUserId(loginUser.getId());
        // 2. 构造成树形结构，也就是 `SysMenuVo`
        //2.1通过stream.filter()过滤找出所有父级菜单
        List<SysMenuVo> menuVoList = menuList.stream().filter(e -> e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                .map(e -> {
                    //构造SysMenuVo
                    SysMenuVo sysMenuVo = new SysMenuVo();
                    //拷贝属性
                    BeanUtils.copyProperties(e, sysMenuVo);
                    return sysMenuVo;
                })
                .map(e -> {
                    //构造树形结构
                    e.setChildren(getChildren(e, menuList));
                    if (e.getChildren() == null) {
                        e.setChildren(new ArrayList<>(0));
                    }
                    return e;
                }).collect(Collectors.toList());
        // 3. 构造成路由树型数据
        return buildMenus(menuVoList);
    }

    /**
     * 构造路由树形结构数据
     * @param menuVoList
     * @return
     */
    private List<RouterVo> buildMenus(List<SysMenuVo> menuVoList) {
        //查询方法我们先不写，先将构造路由的方法写出来。这个方法的逻辑如下
        //1.遍历上面的菜单树
        List<RouterVo> collect = menuVoList.stream().map(e -> {
            //2.创建 RouterVo 对象，将菜单数据转换成路由视图对象
            RouterVo router = new RouterVo();
            router.setHidden(CoreConstant.HIDDEN_STATE.equals(e.getVisible()));
            router.setName(e.getMenuName());
            //2.1构造跳转路径，用多个 '/' 拼接
            router.setPath(getRouterPath(e));
            //2.2构造组件路径，用多个 '/' 拼接
            router.setComponent(getComponent(e));
            //2.3构造meta数据
            router.setMeta(new RouterVo.MetaVo(e.getMenuName(), e.getIcon()));
            //3.如果当前是目录，并且子菜单不为空，就递归构造子菜单
            List<SysMenuVo> children = e.getChildren();
            if (!CollectionUtils.isEmpty(children) && StateEnums.FOLDER.getCode().equals(e.getMenuType())) {
                //如果是目录的话,当然要展示
                router.setAlwaysShow(true);
                //设置路由
                router.setRedirect(CoreConstant.NO_REDIRECT);
                //递归构造菜单
                router.setChildren(buildMenus(children));
            } else {
                //子菜单为空的情况下，children不能给null，否则会报错
                router.setChildren(new ArrayList<>(0));
            }
            return router;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 构造组件路径
     * @param e
     * @return
     */
    private String getComponent(SysMenuVo e) {
        String compnent = CoreConstant.DEFAULT_COMPONENT;
        if (StringUtils.isNotEmpty(e.getComponentUrl())){
            compnent = e.getComponentUrl();
        }
        return compnent;


    }

    /**
     * 构造路由路径
     * @param e
     * @return
     */
    private String getRouterPath(SysMenuVo e) {
        //如果是递归操作则一个一个/拼接在一块
        if (StateEnums.FOLDER.getCode().equals(e.getMenuType())){
            return CoreConstant.URL_SPLIT + e.getRouterPath();
        }else {
            return e.getRouterPath();
        }
    }


}
