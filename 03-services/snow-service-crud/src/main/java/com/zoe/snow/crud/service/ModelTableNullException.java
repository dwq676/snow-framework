package com.zoe.snow.crud.service;

/**
 * ModelTableNullException
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/26
 */
public class ModelTableNullException extends RuntimeException {
    private String message;

    public ModelTableNullException(String message) {
        super();
        this.message = message;
    }
}
