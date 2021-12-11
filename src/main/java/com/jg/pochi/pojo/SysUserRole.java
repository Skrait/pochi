package com.jg.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2021/12/10 23:20
 */
@Data
public class SysUserRole implements Serializable {

    /**
     * 编号
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
