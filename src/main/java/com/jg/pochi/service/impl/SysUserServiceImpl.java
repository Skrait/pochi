package com.jg.pochi.service.impl;

import com.jg.pochi.mapper.SysUserMapper;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author Peekaboo
 * Date 2021/11/25 10:29
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void updateLoginTime(String username) {
        sysUserMapper.updateLoginTime(username);
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.getByUsername(username);
    }
}
