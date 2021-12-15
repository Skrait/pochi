package com.jg.pochi.controller;

import com.jg.pochi.common.Page;
import com.jg.pochi.common.Result;
import com.jg.pochi.pojo.SysMenu;
import com.jg.pochi.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    @RequestMapping(value = "/save",method = RequestMethod.PUT)
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
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysMenu>> getByPage(@RequestBody Page<SysMenu> page) {
        page = sysMenuService.getByPage(page);
        return new Result<>(page);
    }
}
