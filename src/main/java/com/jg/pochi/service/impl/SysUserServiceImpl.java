package com.jg.pochi.service.impl;

import com.jg.pochi.common.Page;
import com.jg.pochi.enums.StateEnums;
import com.jg.pochi.mapper.SysUserMapper;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.service.SysUserService;
import com.jg.pochi.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author Peekaboo
 * Date 2021/11/25 10:29
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private IdWorker idWorker;

    @Override
    public void updateLoginTime(String username) {
        sysUserMapper.updateLoginTime(username);
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.getByUsername(username);
    }

    @Override
    public void save(SysUser sysUser) {
        //ID用雪花算法生成
        sysUser.setId(idWorker.nextId());
        sysUserMapper.save(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void delete(long id) {
        sysUserMapper.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Long id) {
        //先查再改
        SysUser sysUser =  sysUserMapper.getById(id);
        sysUser.setStatus(StateEnums.ENABLED.getCode());
        sysUserMapper.updateStatus(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(Long id) {
        //先查再改
        SysUser sysUser =  sysUserMapper.getById(id);
        sysUser.setStatus(StateEnums.NOT_ENABLE.getCode());
        sysUserMapper.updateStatus(sysUser);
    }

    @Override
    public Page<SysUser> getByPage(Page<SysUser> page) {
        //设置默认的当前页和每页条数
        Integer pageNumber = page.getPageNumber();
        if (pageNumber == null || pageNumber < 1){
            pageNumber = 1;//默认显示第一页
            page.setPageNumber(pageNumber);//默认每页条数20个
        }
        List<SysUser> userList = sysUserMapper.getByPage(page);
        Integer totalCount = sysUserMapper.countByPage(page);//获得总条数
        page.setList(userList);//获得分页数据
        page.setTotalCount(totalCount);//在设置总条数时，计算并设置总页数
        return page;
    }

    @Override
    public SysUser get(Long id) {
        return sysUserMapper.getById(id);
    }
}
