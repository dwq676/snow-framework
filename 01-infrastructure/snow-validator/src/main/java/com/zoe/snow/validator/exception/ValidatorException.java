package com.zoe.snow.validator.exception;

import com.zoe.snow.model.Model;

/**
 * ValidatorException
 *
 * @author Dai Wenqing
 * @date 2016/10/19
 */
public interface ValidatorException {
    void setMessage(Class<? extends Model> classZ, String fieldName, String message);
}
