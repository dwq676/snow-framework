/**
 *
 */
package com.zoe.snow.model.annotation;

import java.lang.annotation.*;

/**
 * 定义字段的额外属性，系列化
 *
 * @author lpw
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    /**
     * 名称
     *
     * @return 类型名称或方法名称。
     */
    String name() default "";

    /**
     * 字段排序
     * @return
     */
    int order() default 0;
}
