package com.jg.pochi.content;

import com.jg.pochi.pojo.SysLog;
import lombok.Data;

/**
 * Author Peekaboo
 * 线程上下文,ThreadLocal是JDK提供的,如果创建一个ThreadLocal变量
 * 那么每个访问这个变量的线程都会有这个变量的副本,操作的是本地的内存
 * 从而规避了线程不安全的问题
 * 注意,线程用完以后一定要remove本地内存中的本地变量
 * @Date 2021/11/21 23:04
 */
@Data
public class SystemContext {

    //封装日志实体
    private SysLog sysLog;

    //本地线程上下文
    private static ThreadLocal<SystemContext> threadLocal = new ThreadLocal<>();

    //获取当前线程上下文
    public static SystemContext get(){
        if (threadLocal.get() == null){
            SystemContext systemContext = new SystemContext();
            systemContext.setSysLog(new SysLog());
            threadLocal.set(systemContext);
        }
        return threadLocal.get();
    }


    public static void remove(){
        threadLocal.remove();
    }
}
