package com.jg.pochi.service.impl;

import com.jg.pochi.mapper.SysLogMapper;
import com.jg.pochi.pojo.SysLog;
import com.jg.pochi.service.SysLogService;
import com.jg.pochi.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 14:05
 */
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void save(SysLog sysLog) {
        sysLog.setCreatedBy(idWorker.nextId() + "");
        sysLogMapper.save(sysLog);
    }
}
