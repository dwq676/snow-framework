package com.zoe.snow.auth;

import java.lang.annotation.*;

/**
 * 不需要验证权限
 *
 * @author Dai Wenqing
 * @date 2016/11/11
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNeedVerify {
    //同时不需要验证token有效性,正常只有验证token的接口才会为true，其余为false
    boolean NoNeedEffectiveness() default false;
}
