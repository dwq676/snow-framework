package com.zoe.snow.model.annotation;

import java.lang.annotation.*;

/**
 * Unique
 *
 * @author Dai Wenqing
 * @date 2016/5/23
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    /**
     * 第几个位置上的参数
     *
     * @return
     */
    int value() default 0;
}
