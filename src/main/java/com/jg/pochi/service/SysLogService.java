package com.jg.pochi.service;

import com.jg.pochi.pojo.SysLog;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 14:03
 */
public interface SysLogService {

    /**
     * 保存日志
     */
    void save(SysLog sysLog);
}
