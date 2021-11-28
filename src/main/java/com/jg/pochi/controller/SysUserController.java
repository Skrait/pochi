package com.jg.pochi.controller;

import com.jg.pochi.common.Result;
import com.jg.pochi.enums.ResultEnums;
import com.jg.pochi.pojo.SysUser;
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
