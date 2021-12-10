package com.jg.pochi.pojo.vo;

import com.jg.pochi.pojo.SysRole;
import lombok.Data;

/**
 * 系统用户视图类,VO与前端对应因此不需要实现序列化
 * Author Peekaboo
 * Date 2021/12/10 17:19
 */
@Data
public class SysUserVo {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名
     */
    private String password;
    /**
     * 微信的openid
     */
    private String openId;

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
     * 账号启用状态，1是0否
     */
    private Integer status;

    /**
     * 角色
     */
    private SysRole sysRole;
}
