package com.jg.pochi.aop;

import java.lang.annotation.*;

/**
 * Author Peekaboo
 * Date 2021/12/25 22:47
 * TYPE代表可以放在累上面Method代表可以放在方法上
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}
