package com.zoe.snow.model;

/**
 * ResultSet
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/12
 */
public interface ResultSet<T> {
    boolean isSuccess();

    T getData();

    String getMessage();

    String getCode();
}
