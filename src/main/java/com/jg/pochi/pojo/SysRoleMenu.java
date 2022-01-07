package com.jg.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色和菜单关联表实体类
 * Author Peekaboo
 * Date 2021/12/26 22:26
 */
@Data
public class SysRoleMenu implements Serializable {

    /**
     * 主键，自增
     */
    private Long id;

    /**
     * 角色编号
     */
    private Long roleId;

    /**
     * 菜单编号
     */
    private Long menuId;
}
