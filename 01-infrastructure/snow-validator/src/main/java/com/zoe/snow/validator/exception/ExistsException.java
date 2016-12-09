package com.zoe.snow.validator.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * ExistsException
 *
 * @author Dai Wenqing
 * @date 2016/5/25
 */
public class ExistsException extends RuntimeException {
    /**
     * 字段代码
     */
    private Object[] code;
    private String modelName;

    public ExistsException(Object... code) {
        super();
        this.code = code;
    }

    public String getMessage() {
        List<String> codeList = new ArrayList<>();
        for (Object o : code) {
            codeList.add(o.toString());
        }
        return String.join(",", codeList);
    }

    public ExistsException setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }
}
