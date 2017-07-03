package com.zoe.snow.json;

import com.zoe.snow.Global;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间反系列化
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/1/2
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
    private String format = "";

    public JsonDateValueProcessor() {
        super();
    }

    public JsonDateValueProcessor(String format) {
        super();
        this.format = format;
    }

    @Override
    public Object processArrayValue(Object paramObject,
                                    JsonConfig paramJsonConfig) {
        return process(paramObject);
    }

    @Override
    public Object processObjectValue(String paramString, Object paramObject,
                                     JsonConfig paramJsonConfig) {
        return process(paramObject);
    }


    private Object process(Object value) {
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(Global.dateTimeFormat, Locale.CHINA);
            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }
}
