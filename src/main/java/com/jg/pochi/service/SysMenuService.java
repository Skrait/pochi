package com.jg.pochi.service;/**
 * Author Peekaboo
 * Date 2021/12/15 11:45
 */

import com.jg.pochi.common.Page;
import com.jg.pochi.pojo.SysMenu;

/**
 * @Auther Song Kang
 * @Date 2021/12/15
 */
public interface SysMenuService {

    /**
     * 添加菜单
     * @param sysMenu
     */
    void save(SysMenu sysMenu);

    /**
     * 修改菜单
     * @param sysMenu
     */
    void update(SysMenu sysMenu);

    /**
     * 根据ID删除菜单信息
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysMenu get(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysMenu> getByPage(Page<SysMenu> page);
}
