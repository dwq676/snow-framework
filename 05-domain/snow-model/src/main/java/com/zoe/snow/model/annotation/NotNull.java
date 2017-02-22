package com.zoe.snow.model.annotation;

import java.lang.annotation.*;

/**
 * 对方法参数增加注解判断是否允许为空
 *
 * @author Dai Wenqing
 * @date 2016/5/23
 */
@Documented
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    /**
     * 第几个位置上的参数
     * 
     * @return
     */
    int value() default 0;
}
