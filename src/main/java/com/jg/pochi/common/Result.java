package com.jg.pochi.common;

import com.jg.pochi.enums.ResultEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * 发现规律
 * @Date 2021/11/19 18:10
 */
@Data
public class Result<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public Result(){
        this.code = ResultEnums.SUCCESS.getCode();
        this.msg = ResultEnums.ERROR.getMsg();
    }

    public Result(String msg){
        this.code = ResultEnums.SUCCESS.getCode();
        this.msg = msg;
    }

    public Result(T data){
        this.code = ResultEnums.SUCCESS.getCode();
        this.msg = ResultEnums.ERROR.getMsg();
    }

    public Result(String msg, T data){
        this.code = ResultEnums.SUCCESS.getCode();
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultEnums resultEnums, String msg) {
        this.code = resultEnums.getCode();
        this.msg = msg;
    }

    public Result(ResultEnums resultEnums) {
        this.code = resultEnums.getCode();
        this.msg = resultEnums.getMsg();
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
