package com.jg.pochi.exception;

import com.jg.pochi.enums.ResultEnum;

/**
 * 自定义异常
 * @Author Peekaboo
 * @Date: 2021/11/19 22:59
 */
public class PochiException extends RuntimeException{
    //相比entends Exception，RuntimeException就不用在每个方法加try-catch手动去抛了。
    private static final long serialVersionUID = 2450214686001409867L;

    private Integer errorCode = ResultEnum.ERROR.getCode();

    public PochiException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.errorCode = resultEnum.getCode();
    }

    public PochiException(ResultEnum resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.errorCode = resultEnum.getCode();
    }

    public PochiException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public PochiException(String msg) {
        super(msg);
    }

    public PochiException(Throwable throwable) {
        super(throwable);
    }

    public PochiException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
