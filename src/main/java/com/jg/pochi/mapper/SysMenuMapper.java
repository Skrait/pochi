package com.jg.pochi.mapper;/**
 * Author Peekaboo
 * Date 2021/12/15 11:32
 */

import com.jg.pochi.common.Page;
import com.jg.pochi.pojo.SysMenu;
import com.jg.pochi.pojo.vo.SysMenuVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther Song Kang
 * @Date 2021/12/15
 */
@Component
public interface SysMenuMapper {

    /**
     * 添加菜单
     * @param sysMenu
     */
    void save(SysMenu sysMenu);

    /**
     * 根据父级菜单ID和名称查询
     * @return
     */
    SysMenu getParentIdAndName(SysMenu sysMenu);

    /**
     * 修改菜单
     * @param sysMenu
     */
    void update(SysMenu sysMenu);

    /**
     * 根据ID删除菜单信息
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysMenu getById(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<SysMenu> getByPage(Page<SysMenu> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysMenu> page);

    /**
     * 查询所有菜单信息
     * @return
     */
    List<SysMenu> getAll();

    /**
     * 根据角色ID查询被选中的菜单ID
     * @param roleId
     */
    List<SysMenu> getRoleSelectMenu(Long roleId);
}
