package com.jg.pochi.exception;

import com.jg.pochi.enums.ResultEnums;

/**
 * 自定义异常
 * @Author Peekaboo
 * @Date: 2021/11/19 22:59
 */
public class PochiException extends RuntimeException{
    //相比entends Exception，RuntimeException就不用在每个方法加try-catch手动去抛了。
    private static final long serialVersionUID = 2450214686001409867L;

    private Integer errorCode = ResultEnums.ERROR.getCode();

    public PochiException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.errorCode = resultEnums.getCode();
    }

    public PochiException(ResultEnums resultEnums, Throwable throwable) {
        super(resultEnums.getMsg(), throwable);
        this.errorCode = resultEnums.getCode();
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
