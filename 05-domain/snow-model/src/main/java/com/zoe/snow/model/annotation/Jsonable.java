/**
 *
 */
package com.zoe.snow.model.annotation;

import java.lang.annotation.*;

/**
 * 框架算定系列化根据此注解决定该字段是否系列化。
 *
 * @author lpw
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Jsonable {
    /**
     * 数据格式。
     *
     * @return 数据格式。
     */
    String format() default "";
}
