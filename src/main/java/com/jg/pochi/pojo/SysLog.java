package com.jg.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * 系统日志实体pojo
 * @Date 2021/11/20 10:42
 */
@Data
public class SysLog implements Serializable {
    /**
     * ID. 雪花算法
     */
    private Long logId;

    /**
     * 请求地址
     */
    private String logUrl;

    /**
     * 参数
     */
    private String logParams;

    /**
     * 状态，1正常，0异常
     */
    private Integer logStatus;

    /**
     * 异常文本
     */
    private String logMessage;

    /**
     * 浏览器UA标识
     */
    private String logUa;

    /**
     * controller
     */
    private String logController;

    /**
     * 请求方式，GET、POST等等
     */
    private String logMethod;

    /**
     * 响应时间
     */
    private Long logTime;

    /**
     * 返回值
     */
    private String logResult;

    /**
     * 请求ip
     */
    private String logIp;

    /**
     * 创建时间
     */
    private String createdDate;

    /**
     * 创建人账号
     */
    private String createdBy;

}
