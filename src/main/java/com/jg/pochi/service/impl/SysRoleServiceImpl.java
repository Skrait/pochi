package com.jg.pochi.service.impl;

import com.jg.pochi.common.Page;
import com.jg.pochi.mapper.SysRoleMapper;
import com.jg.pochi.mapper.SysRoleMenuMapper;
import com.jg.pochi.pojo.SysRole;
import com.jg.pochi.pojo.SysRoleMenu;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.vo.SysRoleVo;
import com.jg.pochi.service.SysRoleService;
import com.jg.pochi.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Peekaboo
 * Date 2021/12/7 16:54
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleVo sysRole) {
        //设置角色的创建人和修改人为用户名
        //先从shiro获取登录用户
        SysUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        //创建Sysrole对象
        SysRole role = new SysRole();
        //拷贝属性
        BeanUtils.copyProperties(sysRole,role);
        role.setCreateBy(username);
        role.setUpdateBy(username);
        sysRoleMapper.save(role);
        //下面开始添加角色权限数据
        saveRoleMenu(sysRole, role);

    }

    /**
     * 修改角色
     * @param sysRole
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleVo sysRole) {
        //设置更新人
        SysUser loginUser = ShiroUtils.getLoginUser();
        String username = loginUser.getUsername();
        SysRole role = new SysRole();
        BeanUtils.copyProperties(sysRole,role);

        //不管添没添加，即是否点击权限选项,先走一遍删除权限
        sysRoleMenuMapper.deleteRoleById(role.getRoleId());

        //下面开始添加角色权限数据
        saveRoleMenu(sysRole, role);

    }

    /**
     * 保存角色数据
     * @param sysRole
     * @param role
     */
    private void saveRoleMenu(SysRoleVo sysRole, SysRole role) {
        if (CollectionUtils.isEmpty(sysRole.getAuthIds())){
            //根据当前角色拥有的菜单ID集合构造List<sysRoleMenu>
            List<SysRoleMenu> roleMenuList = sysRole.getAuthIds().stream().map(id -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(role.getRoleId());
                sysRoleMenu.setMenuId(id);
                return sysRoleMenu;
            }).collect(Collectors.toList());
            sysRoleMenuMapper.saveBatch(roleMenuList);
        }
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
        SysRole sysRole = sysRoleMapper.get(id);
        //拷贝属性
        SysRoleVo vo = new SysRoleVo();
        BeanUtils.copyProperties(sysRole,vo);
        //根据这个角色ID查询所有权限
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.getByRoleId(id);
        //如果角色ID不为空，则取出菜单ID集合
        if (!CollectionUtils.isEmpty(roleMenuList)){
            //取出权限ID
            List<Long> authIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            //roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList())
        vo.setAuthIds(authIds);
        }
        return vo;
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