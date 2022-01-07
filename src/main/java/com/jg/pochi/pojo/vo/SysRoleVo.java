package com.jg.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色视图类
 * VO 用于保存数据的对象,也称作View Object视图对象,用来保存对应页面展示数据
 * Author Peekaboo
 * Date 2021/12/26 16:06
 */
@Data
public class SysRoleVo implements Serializable {
    /**
     * 角色ID，自增
     */
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 排序值，越小越靠前
     */
    private Integer roleSort;

    /**
     * 当前角色所拥有的菜单权限的ID的集合的数组
     */
    private List<Long> authIds;

}
