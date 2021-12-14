package com.jg.pochi.service.impl;

import com.jg.pochi.common.Page;
import com.jg.pochi.enums.StateEnums;
import com.jg.pochi.mapper.SysRoleMapper;
import com.jg.pochi.mapper.SysUserMapper;
import com.jg.pochi.mapper.SysUserRoleMapper;
import com.jg.pochi.pojo.SysRole;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.SysUserRole;
import com.jg.pochi.pojo.vo.SysUserVo;
import com.jg.pochi.service.SysUserService;
import com.jg.pochi.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public void updateLoginTime(String username) {
        sysUserMapper.updateLoginTime(username);
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.getByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserVo sysUser) {
        //拷贝属性
        SysUser user = new SysUser();
        BeanUtils.copyProperties(sysUser,user);
        //ID用雪花算法生成
        sysUser.setId(idWorker.nextId());
        sysUserMapper.save(sysUser);
        //如果角色id存在，则存入用户角色表
        if(sysUser.getSysRole().getRoleId() != null && sysUser.getSysRole() != null){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(sysUser.getSysRole().getRoleId());
            sysUserRoleMapper.save(sysUserRole);
        }
    }

    /**
     * 修改用户
     * @param sysUser
     */
    @Override
    public void update(SysUserVo sysUser) {
        //拷贝属性,保证耦合性
        SysUser user = new SysUser();
        BeanUtils.copyProperties(sysUser,user);
        sysUserMapper.update(user);
        //不管前端有没有选择角色,我们都先把旧角色信息删掉,再添加新的角色信息
        sysUserRoleMapper.deleteByUserId(user.getId());
        //如果角色id存在，贼存入用户角色表
        if (sysUser.getSysRole() != null && sysUser.getSysRole().getRoleId() != null){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysUser.getSysRole().getRoleId());
            sysUserRole.setUserId(sysUser.getId());
            sysUserRoleMapper.save(sysUserRole);
        }
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
    public SysUserVo get(Long id) {
         SysUser user = sysUserMapper.getById(id);
         //拷贝信息
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(user,sysUserVo);
        //查询角色信息(用一个userId查询多个roleId集合,再根据多个roleId集合查询角色信息,最后封装进来)
        List<SysUserRole> sysUserRoleList =  sysUserRoleMapper.getByUserId(user.getId());
        if (!CollectionUtils.isEmpty(sysUserRoleList)){
            //说明有角色信息,取出角色ID
            List<Long> roleIds = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            //根据角色ID查询所有的角色信息
            List<SysRole> roleList =  sysRoleMapper.getByIds(roleIds);
            if (!CollectionUtils.isEmpty(roleList)){
                sysUserVo.setSysRole(roleList.get(0));
            }
        }else {
            sysUserVo.setSysRole(new SysRole());
        }
        return sysUserVo;

    }
}
