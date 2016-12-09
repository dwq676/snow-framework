package com.zoe.snow.validator.exception;

import com.zoe.snow.model.Model;
import org.springframework.stereotype.Component;

/**
 * ModelFieldNullException
 *
 * @author Dai Wenqing
 * @date 2016/10/17
 */
@Component("snow.exception.validator.not-empty")
public class NotEmptyException extends RuntimeException implements ValidatorException {
    private String fieldName;
    private Class<? extends Model> modelClass;
    private String message;

    /*public NotEmptyException(Class<? extends Model> modelClass, String fieldName) {
        this.fieldName = fieldName;
        this.modelClass = modelClass;
    }*/

    @Override
    public void setMessage(Class<? extends Model> classZ, String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
        this.modelClass = classZ;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<? extends Model> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<? extends Model> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
