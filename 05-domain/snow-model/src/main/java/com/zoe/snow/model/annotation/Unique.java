package com.zoe.snow.model.annotation;

import java.lang.annotation.*;

/**
 * Unique
 *
 * @author Dai Wenqing
 * @date 2016/5/23
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
}
