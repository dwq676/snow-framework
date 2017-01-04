package com.zoe.snow.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zoe.snow.Global;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 算定义反系列化类
 *
 * @author Dai Wenqing
 * @date 2016/11/22
 */
public class CustomDateDeSerializer extends JsonDeserializer {
    private SimpleDateFormat sdf = new SimpleDateFormat(Global.dateTimeFormat);

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        Date date = null;
        try {
            date = sdf.parse(jp.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
