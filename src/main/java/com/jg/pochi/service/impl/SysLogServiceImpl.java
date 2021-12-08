package com.jg.pochi.service.impl;

import com.jg.pochi.mapper.SysLogMapper;
import com.jg.pochi.pojo.SysLog;
import com.jg.pochi.service.SysLogService;
import com.jg.pochi.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 14:05
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private IdWorker idWorker;

    @Override
    public void save(SysLog sysLog) {
        sysLog.setLogId(idWorker.nextId());
        sysLog.setCreatedBy("admin");
        sysLogMapper.save(sysLog);
    }
}
