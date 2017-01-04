package org.jboss.resteasy.core;

import com.zoe.snow.Global;
import com.zoe.snow.log.Logger;
import com.zoe.snow.model.ModelHelper;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import com.zoe.snow.ws.Auto;
import org.apache.commons.beanutils.ConvertUtils;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.util.Encode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Json格式注入器
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2016/12/22
 */
public class AutoInjector implements ValueInjector {
    private Class type;
    private Type genericType;
    private String paramName;
    private Auto auto;

    public AutoInjector(Class type, Type genericType, String paramName, Annotation[] annotations) {
        this.type = type;
        this.genericType = genericType;
        this.paramName = paramName;
        if (annotations.length > 0)
            auto = Auto.class.cast(annotations[0]);
    }

    @Override
    public Object inject() {
        return null;
    }

    @Override
    public Object inject(HttpRequest request, HttpResponse response) {
        Object object = null;
        if (type != null) {
            try {
                if (request.getHttpMethod().toUpperCase().equals("GET")) {
                    object = request.getUri().getQueryParameters().getFirst(auto.value());
                } else {
                    object = request.getFormParameters().getFirst(auto.value().trim());
                }
                if (object != null) {
                    object = Encode.decode(object.toString());
                    if (getJSONType(object.toString()).equals(JsonType.JSON_TYPE_ERROR)) {
                        if (type.equals(Date.class))
                            return Converter.toDate(object.toString());
                        return ConvertUtils.convert(object, type);
                    } else if (getJSONType(object.toString()).equals(JsonType.JSON_TYPE_ARRAY)) {
                        //JSONArray jn = JSONArray.fromObject(object);
                        return ModelHelper.fromJsonArray(object.toString(), type);
                    } else if (getJSONType(object.toString()).equals(JsonType.JSON_TYPE_OBJECT)) {
                        //JSONObject jo = JSONObject.fromObject(object);
                        return ModelHelper.fromJson(object.toString(), type);
                    }
                } else {
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
                }
            } catch (Exception e) {
                Logger.error(e, "类型转换出错了");
            }
        }
        return object;
    }

    /**
     * 获取JSON类型
     * 判断规则
     * 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文
     *
     * @param str
     * @return
     */
    private JsonType getJSONType(String str) {
        if (Validator.isEmpty(str)) {
            return JsonType.JSON_TYPE_ERROR;
        }

        char[] strChar = str.substring(0, 1).toCharArray();
        char firstChar = strChar[0];

        if (firstChar == '{') {
            return JsonType.JSON_TYPE_OBJECT;
        } else if (firstChar == '[') {
            return JsonType.JSON_TYPE_ARRAY;
        } else {
            return JsonType.JSON_TYPE_ERROR;
        }
    }

    public enum JsonType {
        /**
         * JSONObject
         */
        JSON_TYPE_OBJECT,
        /**
         * JSONArray
         */
        JSON_TYPE_ARRAY,
        /**
         * 不是JSON格式的字符串
         */
        JSON_TYPE_ERROR
    }
}
