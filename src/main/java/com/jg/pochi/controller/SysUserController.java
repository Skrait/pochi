package com.jg.pochi.controller;

import com.jg.pochi.common.Page;
import com.jg.pochi.common.Result;
import com.jg.pochi.enums.ResultEnums;
import com.jg.pochi.pojo.SysUser;
import com.jg.pochi.pojo.vo.SysUserVo;
import com.jg.pochi.pojo.vo.TokenVo;
import com.jg.pochi.service.SysUserService;
import com.jg.pochi.shiro.SysUserRealm;
import com.jg.pochi.utils.ShiroUtils;
import com.jg.pochi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2021/11/25 9:04
 */
@RestController
@RequestMapping("/sysUser")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping("/save")
    public Result<?> save(@RequestBody SysUserVo sysUser){

        //save前做好做参数校验,参数校验最好放在Controller里面去,这样可以少抛些异常
        if(StringUtils.isBlank(sysUser.getUsername())){
            return new Result<>(ResultEnums.PARAMS_NULL,"用户名不能为空");
        }
        if (StringUtils.isBlank(sysUser.getPassword())){
            return new Result<>(ResultEnums.LOGIN_PARAM_ERROR,"密码不能为空");
        }
        sysUserService.save(sysUser);
        return new Result<>("添加成功");

    }

    /**
     * 修改接口一般不提供密码修改功能,若提供会导致业务非常复杂,需考虑以下三种
     * 一般会专门提供一个修改密码的功能
     * 1、用户名没输入密码
     * 2、用户输入了密码
     * 3、用户没输入密码,传入的是旧密码
     * @param sysUser
     * @return
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysUser sysUser){

        sysUserService.update(sysUser);
        return new Result<>("修改成功");

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable long id){
        sysUserService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 启用用户,由于实际上是update,因此用PUT
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable/{id}",method = RequestMethod.PUT)
    public Result<?> enable(@PathVariable Long id){
        sysUserService.enable(id);
        return new Result<>("启用成功");
    }

    /**
     * 封禁用户
     * @param id
     * @return
     */
    @PutMapping("/disable/{id}")
    public Result<?> disable(@PathVariable Long id){
        sysUserService.disable(id);
        return new Result<>("禁用成功");
    }

    /**
     * 分页查询,分页查询一般也要传一个page?
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysUser>> getByPage(@RequestBody Page<SysUser> page) {
        page = sysUserService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<SysUser> get(@PathVariable Long id) {
        System.out.println(id);
        SysUser sysUser = sysUserService.get(id);
        return new Result<>(sysUser);
    }

    /**
     * 登录
     * 这里可以返回一个Map但不建议,推荐返回实体类以便后期维护一眼明了返回的是什么
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<TokenVo> login(@RequestBody SysUser sysUser) {
        // 校验用户名密码
        if (sysUser == null || StringUtils.isBlank(sysUser.getUsername()) || StringUtils.isBlank(sysUser.getPassword())) {
            return new Result<>(ResultEnums.LOGIN_PARAM_ERROR);
        }
        // 使用shiro进行登录
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
        try {
            subject.login(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultEnums.LOGIN_PARAM_ERROR);
        }
        // 登录成功
        Serializable sessionId = subject.getSession().getId();
        // 更新登录时间
        sysUserService.updateLoginTime(sysUser.getUsername());
        return new Result<>(new TokenVo(sessionId));
    }




    /**
     * 获取登录用户
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<SysUser> info() {
        SysUser sysUser = ShiroUtils.getLoginUser();
        return new Result<>(sysUser);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result<?> logout() {
        SecurityUtils.getSubject().logout();
        return new Result<>("退出成功");
    }
}
