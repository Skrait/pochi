package com.jg.pochi.enums;

import lombok.Getter;

/**
 * Author Peekaboo
 * 返回码枚举
 * @Date 2021/11/19 18:02
 */
@Getter
public enum ResultEnums {
    /**
     * 除20000外，其余都是失败
     * 每个返回码代码代表具体失败场景
     */
    SUCCESS(20000,"操作成功"),
    ERROR(40000, "操作失败！"),
    DATA_NOT_FOUND(40001, "查询失败！"),
    PARAMS_NULL(40002, "参数不能为空！"),
    PARAMS_ERROR(40005, "参数不合法！"),
    NO_LOGIN(40006,"用户未登录"),
    LOGIN_PARAM_ERROR(40007,"用户名或密码错误"),
    MENU_EXISTS(40008,"菜单已存在"),
;
    private Integer code;
    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;

    }
}
