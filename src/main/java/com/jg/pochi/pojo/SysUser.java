package com.jg.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 *
 * Date 2021/11/25 8:59
 */
@Data
public class SysUser implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 微信的openid
     */
    private String openId;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String header;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 最后登录时间
     */
    private String loginTime;

    /**
     * 账号启用状态，1是0否
     */
    private Integer status;

    /**
     * 是否删除，1是0否
     */
    private Integer deleted;

}
