package com.jg.pochi.mapper;/**
 * Author Peekaboo
 * Date 2021/11/25 17:49
 */

import com.jg.pochi.common.Page;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * 添加用户
     * @param sysUser
     */
    void save(SysUserVo sysUser);

    /**
     * 修改用户
     * @param sysUser
     */
    void update(SysUser sysUser);

    /**
     * 逻辑删除,因此xml里是update
     * @param id
     */
    void delete(long id);

    /**
     * 更新状态值
     * @param id
     */
    void updateStatus(SysUser sysUser);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SysUser getById(Long id);

    /**
     * 根据page(对象)分页查询
     * @param page
     * @return
     */
    List<SysUser> getByPage(Page<SysUser> page);

    /**
     * 根据id查询用户信息
     * @param page
     * @return
     */
    Integer countByPage(Page<SysUser> page);
}
