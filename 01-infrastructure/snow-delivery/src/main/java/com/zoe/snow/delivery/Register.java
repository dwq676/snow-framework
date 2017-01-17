package com.zoe.snow.delivery;

import java.lang.annotation.*;

/**
 * 服务注册
 *
 * @author Dai Wenqing
 * @date 2016/4/5
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {
    /*
    服务地址
    * */
    String host() default "";

    /*服务端口
    * */
    int port() default 0;

    /*命名空间*/
    String nameSpace() default "";

    /*服务名*/
    String serviceName() default "";

    Verb verb() default Verb.GET;

    String prefix() default "";

    String protocol() default "http";
}
