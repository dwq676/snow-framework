package com.zoe.snow.model;

import com.zoe.snow.Global;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Encode;
import com.zoe.snow.util.Validator;
import org.apache.commons.beanutils.ConvertUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类型转换
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/1/12
 */
public class TypeConverter<T> {
    public static <T> Object converter(Object object, Class<T> type) {
        if (object != null) {
            try {
                object = Encode.decode(object.toString());
                if (getJSONType(object.toString()).equals(Global.JsonType.JSON_TYPE_ERROR)) {
                    if (type.equals(Date.class))
                        return Converter.toDate(object.toString());
                    return ConvertUtils.convert(object, type);
                } else if (getJSONType(object.toString()).equals(Global.JsonType.JSON_TYPE_ARRAY)) {
                    if (type.newInstance() instanceof Model)
                        return ModelHelper.fromJsonArray(object.toString(), (Class<? extends Model>) type);
                } else if (getJSONType(object.toString()).equals(Global.JsonType.JSON_TYPE_OBJECT)) {
                    if (type.newInstance() instanceof Model)
                        return ModelHelper.fromJson(object.toString(), (Class<? extends Model>) type);
                }
            } catch (Exception e) {
            }

        } else {
            //判断是否为基本类型，即值类型,值类型返回默认值或最小值
            return getBasicTypeValue(type);
        }
        return null;
    }

    /**
     * 获取java基本类型的默认值
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> Object getBasicTypeValue(Class<T> type) {
        if (type.equals(Double.TYPE)) {
            return Double.MIN_VALUE;
        } else if (type.equals(Integer.TYPE)) {
            return 0;
        } else if (type.equals(Long.TYPE)) {
            return 0L;
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.ZERO;
        } else if (type.equals(Float.TYPE)) {
            return 0f;
        } else if (type.equals(Byte.TYPE)) {
            return Byte.parseByte("0");
        } else if (type.equals(Character.TYPE)) {
            return Character.NON_SPACING_MARK;
        } else if (type.equals(Boolean.TYPE)) {
            return false;
        }
        return null;
    }

    /**
     * 获取JSON类型
     * 判断规则
     * 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文
     *
     * @param str
     * @return
     */
    private static Global.JsonType getJSONType(String str) {
        if (Validator.isEmpty(str)) {
            return Global.JsonType.JSON_TYPE_ERROR;
        }

        char[] strChar = str.substring(0, 1).toCharArray();
        char firstChar = strChar[0];

        if (firstChar == '{') {
            return Global.JsonType.JSON_TYPE_OBJECT;
        } else if (firstChar == '[') {
            return Global.JsonType.JSON_TYPE_ARRAY;
        } else {
            return Global.JsonType.JSON_TYPE_ERROR;
        }
    }


}