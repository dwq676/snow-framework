package com.zoe.snow.model.annotation;

import java.lang.annotation.*;

/**
 * Validate
 *
 * @author Dai Wenqing
 * @date 2016/3/3
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    Rule[] rules() default {};
}
