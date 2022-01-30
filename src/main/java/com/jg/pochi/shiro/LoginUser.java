package com.jg.pochi.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 在设计模式的思想上，我们应该把一个Entity设计成一个纯粹的pojo
 * 它只能是个实体，只是与数据库对应，或者用来给前端供给数据
 * 不要让它去继承一个基类去实现某些方法
 * @Author: 杨德石
 * @Date: 2020/12/10 21:51
 * @Version 1.0
 */
@Data
public class LoginUser implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 微信小程序openid
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
     * 最后登录时间
     */
    private String loginTime;

    /**
     * 权限列表
     */
    private List<String> auths;

}
