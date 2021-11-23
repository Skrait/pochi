package com.jg.pochi.aop;

import com.alibaba.fastjson.JSON;
import com.jg.pochi.content.SystemContext;
import com.jg.pochi.enums.StateEnums;
import com.jg.pochi.pojo.SysLog;
import com.jg.pochi.service.SysLogService;
import com.jg.pochi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Author Peekaboo
 *
 * Date 2021/11/20 15:42
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 声明切点
     */
    @Pointcut("execution( * com.jg..*.controller..*(..))")

    public void logPointCut() {

    }

    /**
     * 前置通知
     *
     * throws Exception
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        // 获取request
        HttpServletRequest request = attributes.getRequest();
        // 获取请求地址
        String uri = request.getRequestURI();
        // 记录日志
        // 日志输出基本信息
        log.info("请求地址：{}", uri);
        log.info("请求方式：{}", request.getMethod());
        // 获取请求IP
        String remoteIp = StringUtils.getRemoteIp(request);
        log.info("IP：{}", remoteIp);
        // 获取请求的controller
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        log.info("方法：{}.{}", controllerName, joinPoint.getSignature().getName());
        // 记录参数
        Object[] args = joinPoint.getArgs();
        // 记录日志条件：参数不为空，并且第一个参数不是request也不是MultipartFile
        boolean logParamFlag = args != null && args.length > 0 && !(args[0] instanceof ServletRequest) && !(args[0] instanceof MultipartFile);
        SysLog sysLog = SystemContext.get().getSysLog();
        if (logParamFlag) {
            String param = JSON.toJSONString(args[0]);
            log.info("请求参数：{}", param);
            sysLog.setLogParams(param);
        }
        // 记录日志
        sysLog.setLogUrl(uri);
        sysLog.setLogStatus(StateEnums.REQUEST_SUCCESS.getCode());
        sysLog.setLogMethod(request.getMethod());
        sysLog.setLogIp(remoteIp);
        sysLog.setLogUa(request.getHeader("user-Agent"));
        sysLog.setLogController(controllerName);
    }

    /**
     * @param pjp
     * proceed很重要，这个是aop代理链执行的方法。
     * Around 修饰的环绕通知类型，是将整个目标方法封装起来
     * throws Throwable
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
        //记录方法,执行时间
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();
        long time = System.currentTimeMillis() - startTime;
        log.info("方法执行耗时:{}",time);
        SysLog sysLog = SystemContext.get().getSysLog();
        sysLog.setLogTime(time);

        String result = JSON.toJSONString(ob);
        log.info("返回值{}" , result);
        sysLog.setLogResult(result);
        sysLogService.save(sysLog);
        SystemContext.remove();

        return ob;
    }


    /**
     * 异常通知,发生异常走这里
     * @param joinPoint
     * @param throwable
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "throwable")
    public void doException(JoinPoint joinPoint, Throwable throwable){
        SysLog sysLog = SystemContext.get().getSysLog();
        sysLog.setLogStatus(StateEnums.NOT_ENABLE.getCode());
        sysLog.setLogMessage(StateEnums.NOT_ENABLE.getMsg());
        sysLog.setLogTime(0L);
        sysLogService.save(sysLog);
        SystemContext.remove();
    }
}
