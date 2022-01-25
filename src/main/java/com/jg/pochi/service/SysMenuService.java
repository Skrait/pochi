package com.jg.pochi.service;/**
 * Author Peekaboo
 * Date 2021/12/15 11:45
 */

import com.jg.pochi.common.Page;
import com.jg.pochi.pojo.SysMenu;
import com.jg.pochi.pojo.vo.SysMenuVo;

import java.util.List;

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

    /**
     * 查询树形节点
     * @return
     */
    List<SysMenuVo> getTreeList();

    /**
     * 根据角色ID查询被选中的菜单ID集合
     * 这里不查询父级菜单
     */
    List<Long> getRoleSelectMenu(Long roleId);
}
