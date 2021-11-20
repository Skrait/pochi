package com.jg.pochi.aop;

import com.jg.pochi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 15:42
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {

    /**
     * 声明切点
     */
    @Pointcut("execution( * com.jg..*.controller..*(..))")
    public void logPintCut(){

    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("logPintCut()")
    public void deBefore(JoinPoint joinPoint) throws Exception{
        //接收到请求
        ServletRequestAttributes arrtibutes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        assert arrtibutes != null;
        //或区域

        StringUtils.getRemoteIp()


    }

}
