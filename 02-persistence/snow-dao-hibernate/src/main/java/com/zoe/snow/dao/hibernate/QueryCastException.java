package com.zoe.snow.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

/**
 * ExistsException
 *
 * @author Dai Wenqing
 * @date 2016/5/25
 */
public class QueryCastException extends RuntimeException {
    private String message;

    public QueryCastException(String message) {
        super();
        this.message = message;
    }

    public String getCode() {
        return message;
    }
}
