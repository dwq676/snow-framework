package com.zoe.snow.conf.property;

/**
 * 配置实体，用来做配置键值对实体
 *
 * @author Dai Wenqing
 * @date 2016/1/6
 */
public class Element {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
