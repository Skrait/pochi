package com.jg.pochi.advice;

import com.jg.pochi.common.Result;
import com.jg.pochi.exception.PochiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 9:25
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    /**
     * 全局处理自定义异常
     * @ExceptionHandler(PochiException.class), 用来指明异常的处理类型
     * @param exception
     * @return
     */
    @ExceptionHandler(PochiException.class)
    public Result<?> exceptionHandler(PochiException exception){
        log.error("统一异常处理");
        return new Result<>(exception.getErrorCode(),exception.getMessage());
    }
}
