package com.jg.pochi.common;

import com.jg.pochi.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/19 18:10
 */
@Data
public class Result<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public Result(){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.ERROR.getMsg();
    }

    public Result(String msg){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = msg;
    }

    public Result(T data){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.ERROR.getMsg();
    }

    public Result(String msg, T data){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultEnum resultEnum, String msg) {
        this.code = resultEnum.getCode();
        this.msg = msg;
    }

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
