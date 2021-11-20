package com.jg.pochi.mapper;

import com.jg.pochi.pojo.SysLog;
import org.springframework.stereotype.Component;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 10:53
 */
@Component
public interface SysLogMapper {

    /**
     * 保存日志
     */

    void save(SysLog sysLog);
}
