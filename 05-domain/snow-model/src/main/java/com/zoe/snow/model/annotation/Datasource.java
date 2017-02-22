package com.zoe.snow.model.annotation;

import java.lang.annotation.*;

/**
 * 数据源注解，允许单个实体指定数据源
 *
 * @author Dai Wenqing
 * @date 2016/4/5
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Datasource {
    String value() default "";
}
