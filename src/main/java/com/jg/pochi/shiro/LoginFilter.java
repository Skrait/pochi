package com.jg.pochi.shiro;

import com.alibaba.fastjson.JSON;
import com.jg.pochi.common.Result;
import com.jg.pochi.enums.ResultEnums;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Author Peekaboo
 * 重写登录失效后重定向执行的方法
 * @Date 2021/11/23 15:15
 */
public class LoginFilter extends UserFilter {

    /**
     * 用于处理未登录时页面重定向的逻辑
     * 因此只要进入到这个方法,即意味登录失效了
     * 我们只需要在这个方法里,给前端返回一个登陆失败的状态即可
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        //设置响应头是JSON
        response.setContentType("application/json; charset=utf-8");
        //直接写会未登录的JSON报文,从枚举常量中取出来
        response.getWriter().write(JSON.toJSONString(new Result<>(ResultEnums.NO_LOGIN)));
        //super.redirectToLogin(request, response);
    }
}
