package com.zoe.snow.validator;

import com.zoe.snow.message.Message;

import java.lang.annotation.*;

/**
 * Rule
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dao {
}
