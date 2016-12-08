package com.zoe.snow.model.enums;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Operator
 *
 * @author Dai Wenqing
 * @date 2016/2/29
 */
public enum Operator {
    Comma(","),
    And(" and "),
    Or(" or ");

    private String type;
    private Map<String, Operator> map;

    Operator(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Operator get(String type) {
        if (map == null) {
            map = new ConcurrentHashMap<>();
            for (Operator dataType : Operator.values())
                map.put(dataType.getType(), dataType);
        }

        return map.get(type);
    }
}
