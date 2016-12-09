package com.zoe.snow.context.aop.annotation;

import java.lang.annotation.*;

/**
 * Statistics
 *
 * @author Dai Wenqing
 * @date 2016/4/1
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTo {
    /**
     * 日志类型
     *
     * @return
     */
    String value() default "system";

}
