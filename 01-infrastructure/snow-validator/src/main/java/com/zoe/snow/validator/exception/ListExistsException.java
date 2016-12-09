package com.zoe.snow.validator.exception;

/**
 * 批量保存异常列表
 *
 * @author Dai Wenqing
 * @date 2016/11/3
 */
public class ListExistsException extends RuntimeException {
    private Object object;

    public ListExistsException(Object object) {
        this.object = object;
    }

    @Override
    public String getMessage() {
        return object.toString();
    }
}
