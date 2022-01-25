package com.jg.pochi.service.impl;

import com.jg.pochi.common.Page;
import com.jg.pochi.constant.CoreConstant;
import com.jg.pochi.enums.ResultEnums;
import com.jg.pochi.exception.PochiException;
import com.jg.pochi.mapper.SysMenuMapper;
import com.jg.pochi.pojo.SysMenu;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.vo.SysMenuVo;
import com.jg.pochi.service.SysMenuService;
import com.jg.pochi.utils.ShiroUtils;
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
     * 查询树形节点
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
            // 根据顶级菜单的ID，递归从剩余的列表中找子菜单
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
     * 递归构造树形菜单
     * @param sysMenuVo 当前菜单
     * @param menuList 原始菜单数据List<SysMenu>
     * @return
     */
    private List<SysMenuVo> getChildren(SysMenuVo sysMenuVo, List<SysMenu> menuList) {
        // 第一步，直接找到sysMenu的子菜单
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
}
