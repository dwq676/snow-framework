package com.zoe.snow.ws;

import java.lang.annotation.*;

/**
 * 动态转换jsonData
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auto {
    /*query或者form里的key*/
    String value() default "";
}
