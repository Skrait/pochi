package com.jg.pochi.mapper;/**
 * Author Peekaboo
 * Date 2021/11/25 17:49
 */

import com.jg.pochi.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Auther Song Kang
 * @Date 2021/11/25
 */
@Component
public interface SysUserMapper {

    //更新指定用户名的登陆时间为当前时间
    void updateLoginTime(@Param("username") String username);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser getByUsername(@Param("username") String username);
}
