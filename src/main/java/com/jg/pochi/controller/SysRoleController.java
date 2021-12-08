package com.jg.pochi.controller;

import com.jg.pochi.common.Page;
import com.jg.pochi.common.Result;
import com.jg.pochi.pojo.SysRole;
import com.jg.pochi.pojo.vo.SysRoleVo;
import com.jg.pochi.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author Peekaboo
 * Date 2021/12/7 16:56
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping(value="save",method = RequestMethod.POST)
    public Result<?> save(@RequestBody SysRole sysRole){
        sysRoleService.save(sysRole);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return new Result<>("修改成功");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        sysRoleService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<SysRoleVo> get(@PathVariable Long id) {  //@PathVariable为路径变量,接收请求站位符的值
        SysRoleVo sysRole = sysRoleService.get(id);
        return new Result<>(sysRole);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysRole>> getByPage(@RequestBody Page<SysRole> page) {
        page = sysRoleService.getByPage(page);
        return new Result<>(page);
    }
}
