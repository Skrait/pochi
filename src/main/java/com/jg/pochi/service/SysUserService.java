package com.jg.pochi.service;

import com.jg.pochi.pojo.SysUser;

/**
 * Author Peekaboo
 * Date 2021/11/25 10:22
 */
public interface SysUserService {

    /**
     * 根据指定用户名更新时间
     * @param username
     */
    void updateLoginTime(String username);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser getByUsername(String username);
}
