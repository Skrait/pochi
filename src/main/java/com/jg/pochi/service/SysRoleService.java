package com.jg.pochi.service;

import com.jg.pochi.common.Page;
import com.jg.pochi.pojo.SysRole;
import com.jg.pochi.pojo.vo.SysRoleVo;

import java.util.List;

/**
 * @Author Song Kang
 * @Date 2021/12/7
 */
public interface SysRoleService {

    /**
     * 添加角色
     * @param sysRole
     */
    void save(SysRole sysRole);

    /**
     * 修改角色
     * @param sysRole
     */
    void update(SysRole sysRole);

    /**
     * 删除角色
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysRole get(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysRole> getByPage(Page<SysRole> page);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> getAll();
}
