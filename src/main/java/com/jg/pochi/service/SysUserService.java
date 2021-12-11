package com.jg.pochi.service;

import com.jg.pochi.common.Page;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.vo.SysUserVo;

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

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUserVo sysUser);

    /**
     * 修改用户
     * @param sysUser
     */
    void update(SysUserVo sysUser);

    /**
     * 删除用户
     * @param id
     */
    void delete(long id);

    /**
     * 启用
     * @param id
     */
    void disable(Long id);

    /**
     * 禁用
     * @param id
     */
    void enable(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysUser> getByPage(Page<SysUser> page);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysUserVo get(Long id);
}
