package com.jg.pochi.controller;

import com.jg.pochi.aop.LogAnnotation;
import com.jg.pochi.common.Page;
import com.jg.pochi.common.Result;
import com.jg.pochi.pojo.SysMenu;
import com.jg.pochi.pojo.vo.SysMenuVo;
import com.jg.pochi.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author Peekaboo
 * Date 2021/12/15 11:38
 */
@Slf4j
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result<?> save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return new Result<>("添加成功！");
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result<?> update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return new Result<>("修改成功！");
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        sysMenuService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<SysMenu> get(@PathVariable Long id) {
        SysMenu sysMenu = sysMenuService.get(id);
        return new Result<>(sysMenu);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    //加上次注解 代表对此接口记录日志,这里我们用AOP实现
    @LogAnnotation(module="文章",operation="获取文章列表")
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysMenu>> getByPage(@RequestBody Page<SysMenu> page) {
        page = sysMenuService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 查询树形节点
     * @return
     */
    @RequestMapping(value = "/getTreeList", method = RequestMethod.GET)
    public Result<List<SysMenuVo>> getTreeList() {
        List<SysMenuVo> list = sysMenuService.getTreeList();
        return new Result<>(list);
    }

    /**
     * 根据角色ID查询被选中的菜单ID集合
     * 这里不查询父级菜单
     * @param roleId
     * @return
     */
    @GetMapping("/getRoleSelectMenu/{roleId}")
    public Result<List<Long>> getRoleSelectMenu(@PathVariable Long roleId){
        List<Long> roleSelectMenu = sysMenuService.getRoleSelectMenu(roleId);
        return new Result<>(roleSelectMenu);
    }
}
