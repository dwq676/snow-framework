package com.zoe.snow.context.validator;

import com.zoe.snow.message.Message;

import java.lang.annotation.*;
/**
 * Rule
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rule {
    /**
     * 错误代码
     *
     * @return
     */
    Message message() default Message.Error;

    /**
     * 参数
     *
     * @return
     */
    String[] paramter() default {};

    String name() default "";
}
