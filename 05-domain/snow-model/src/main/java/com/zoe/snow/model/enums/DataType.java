package com.zoe.snow.model.enums;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SqlDataType
 *
 * @author Dai Wenqing
 * @date 2015/9/9
 */
public enum DataType {
    Int("int"), Varchar("varchar"), Datetime("datetime");
    private static Map<String, DataType> map;
    private String type;

    DataType(String type) {
        this.type = type;
    }

    public static DataType get(String type) {
        if (map == null) {
            map = new ConcurrentHashMap<>();
            for (DataType dataType : DataType.values())
                map.put(dataType.getType(), dataType);
        }

        return map.get(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
