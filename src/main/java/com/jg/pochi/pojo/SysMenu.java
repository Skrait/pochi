package com.jg.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2021/12/15 11:30
 */
@Data
public class SysMenu implements Serializable {

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 父菜单编号
     */
    private Long parentId;

    /**
     * 排序值
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String routerPath;

    /**
     * 组件路径
     */
    protected String componentUrl;

    /**
     * 菜单类型，1目录，2菜单，3权限
     */
    private Integer menuType;

    /**
     * 是否显示，1是0否
     */
    private Integer visible;

    /**
     * 是否启用，1是0否
     */
    private Integer status;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 是否删除，1是0否
     */
    private Integer deleted;
}
