package com.jg.pochi.service.impl;

import com.jg.pochi.common.Page;
import com.jg.pochi.constant.CoreConstant;
import com.jg.pochi.enums.ResultEnums;
import com.jg.pochi.exception.PochiException;
import com.jg.pochi.mapper.SysMenuMapper;
import com.jg.pochi.pojo.SysMenu;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.service.SysMenuService;
import com.jg.pochi.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

}
