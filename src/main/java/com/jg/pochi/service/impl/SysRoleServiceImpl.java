package com.jg.pochi.service.impl;

import com.jg.pochi.common.Page;
import com.jg.pochi.mapper.SysRoleMapper;
import com.jg.pochi.pojo.SysRole;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.vo.SysRoleVo;
import com.jg.pochi.service.SysRoleService;
import com.jg.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author Peekaboo
 * Date 2021/12/7 16:54
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public void save(SysRole sysRole) {
        //设置角色的创建人和修改人为用户名
        //先从shiro获取登录用户
        SysUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        sysRole.setCreateBy(username);
        sysRole.setUpdateBy(username);
        sysRoleMapper.save(sysRole);
    }

    /**
     * 修改角色
     * @param sysRole
     */
    @Override
    public void update(SysRole sysRole) {
        //设置更新人
        SysUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        sysRole.setUpdateBy(username);
        sysRoleMapper.update(sysRole);
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysRoleMapper.delete(id);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public SysRoleVo get(Long id) {
        return sysRoleMapper.get(id);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<SysRole> getByPage(Page<SysRole> page) {
        //设置默认当前页数
        Integer pageNumber = page.getPageNumber();
        if (pageNumber == null || pageNumber < 1){
            pageNumber = 1;
            page.setPageNumber(pageNumber);
        }
        List<SysRole> roleList = sysRoleMapper.getByPage(page);
        Integer totalCount =  sysRoleMapper.countByPage(page);//获取总条数
        //设置值
        page.setTotalCount(totalCount);
        page.setList(roleList);
        return page;

    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }
}