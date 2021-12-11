package com.jg.pochi.mapper;

import com.jg.pochi.pojo.SysUserRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**

import com.jg.pochi.pojo.SysUserRole;
import org.springframework.stereotype.Component;

/**
 * @Auther Song Kang
 * @Date 2021/12/11
 */
@Component
public interface SysUserRoleMapper {

    /**
     * 添加
     * @param sysUserRole
     */
    void save(SysUserRole sysUserRole);

    /**
     * 根据用户id删除
     * @param id
     */
    void deleteByUserId(Long id);

    /**
     * 根据用户ID查询用户
     * @param id
     * @return
     */
    List<SysUserRole> getByUserId(Long id);
}
