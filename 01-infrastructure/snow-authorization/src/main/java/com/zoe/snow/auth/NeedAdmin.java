package com.zoe.snow.auth;

import java.lang.annotation.*;

/**
 * 排除验证注解控制
 *
 * @author Dai Wenqing
 * @date 2016/11/11
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedAdmin {
}
