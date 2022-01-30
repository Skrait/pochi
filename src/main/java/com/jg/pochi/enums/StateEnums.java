package com.jg.pochi.enums;

import lombok.Getter;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 9:49
 */
@Getter
public enum StateEnums  {

    /**
     * 逻辑删除状态
     */
    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),

    /**
     * 启用状态
     */
    ENABLED(1, "启用"),
    NOT_ENABLE(0, "未启用"),

    /**
     * 性别状态
     */
    SEX_MAN(1, "男"),
    SEX_WOMAN(2, "女"),

    /**
     * 请求访问状态枚举
     */
    REQUEST_SUCCESS(1, "请求正常"),
    REQUEST_ERROR(0, "请求异常"),


    /**
     * 菜单状态枚举
     */
    FOLDER(1,"目录"),
    MENU(2,"菜单"),
    AUTH(3,"权限"),
    ;

    private Integer code;
    private String msg;

    /**
     * 有参构造嘛
     * @param code
     * @param msg
     */
    StateEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
