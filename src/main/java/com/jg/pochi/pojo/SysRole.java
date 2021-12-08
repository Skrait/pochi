package com.jg.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2021/12/7 16:13
 */
@Data
public class SysRole implements Serializable {

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
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 逻辑删除，1是0否
     */
    private Integer delete;
}
