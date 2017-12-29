package com.zoe.snow.model.annotation;

import com.zoe.snow.model.enums.Rules;

import java.lang.annotation.*;

/**
 * Rule
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rule {
    /**
     * 错误代码
     *
     * @return
     */
    Rules rules();

    /**
     * 参数
     *
     * @return
     */
    String[] parameter() default {};

    String name() default "";
}
