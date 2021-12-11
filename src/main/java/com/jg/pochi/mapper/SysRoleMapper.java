package com.jg.pochi.mapper;

import com.jg.pochi.common.Page;
import com.jg.pochi.pojo.SysRole;
import com.jg.pochi.pojo.vo.SysRoleVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther Song Kang
 * @Date 2021/12/7
 */
@Component
public interface SysRoleMapper {

    /**
     * 保存角色
     * @param sysRole
     */
    void save(SysRole sysRole);

    /**
     * 更新角色
     * @param sysRole
     */
    void update(SysRole sysRole);

    /**
     * 删除角色(update)
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysRoleVo get(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<SysRole> getByPage(Page<SysRole> page);


    /**
     * 获取总条数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysRole> page);

    /**
     * 根据ID集合查询所有角色信息
     * @param roleIds
     * @return
     */
    List<SysRole> getByIds(List<Long> roleIds);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> getAll();
}
