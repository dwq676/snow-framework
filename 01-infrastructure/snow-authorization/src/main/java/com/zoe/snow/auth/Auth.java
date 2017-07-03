package com.zoe.snow.auth;

import com.zoe.snow.model.Model;

import java.lang.annotation.*;

/**
 * 对某个对象进行授权
 *
 * @author Dai Wenqing
 * @date 2016/11/8
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    Class<? extends Model> value();

    /**
     * 授权对象
     *
     * @return
     */
    String authTo() default "";

    /**
     * 令牌
     *
     * @return
     */
    String token() default "";
}
