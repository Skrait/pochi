package com.jg.pochi.mapper;/**
 * Author Peekaboo
 * Date 2021/12/26 22:33
 */

import com.jg.pochi.pojo.SysRoleMenu;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther Song Kang
 * @Date 2021/12/26
 */
@Component
public interface SysRoleMenuMapper {

    /**
     * 批量插入菜单List
     * @param roleMenuList
     */
    void saveBatch(List<SysRoleMenu> roleMenuList);


    void deleteRoleById(Long roleId);

    /**
     * 根据角色ID查询
     * @param id
     * @return
     */
    List<SysRoleMenu> getByRoleId(Long id);
}
