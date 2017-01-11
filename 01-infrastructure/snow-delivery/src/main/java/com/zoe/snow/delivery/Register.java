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
    String host();

    /*服务端口
    * */
    int port();

    /*命名空间*/
    String nameSpace();

    /*服务名*/
    String serviceName();

    Verb verb() default Verb.GET;
}
