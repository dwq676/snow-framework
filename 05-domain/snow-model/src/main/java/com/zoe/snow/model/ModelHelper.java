package com.zoe.snow.model;

import com.alibaba.fastjson.JSON;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.model.annotation.Property;
import com.zoe.snow.model.mapper.ModelTable;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.model.support.BaseModel;
import com.zoe.snow.model.support.Sort;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.zoe.snow.util.Validator;
import com.zoe.snow.util.Converter;
import com.zoe.snow.log.Logger;
import net.sf.json.JsonConfig;

import java.lang.reflect.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author daiwenqing
 */
public class ModelHelper {

    @SuppressWarnings("unchecked")
    protected static <T extends Model> Class<T> getModelClass(Class<T> modelClass) {
        if (modelClass.getName().endsWith("Model"))
            return modelClass;

        return getModelClass((Class<T>) modelClass.getSuperclass());
    }

    public static <T extends Model> T fromJson(String json, Class<T> clazz) {
        if (Validator.isEmpty(json))
            return null;
        if (clazz == null)
            return null;
        return JSON.parseObject(json, clazz);
    }

    public static <T extends Model> Collection<T> fromJsonArray(String jsonArray, Class<T> clazz) {
        if (Validator.isEmpty(jsonArray))
            return null;
        if (clazz == null)
            return null;
        return JSON.parseArray(jsonArray, clazz);
    }

    /**
     * 获取初始化MODEL
     *
     * @return
     */
    public static BaseModel getBaseModel() {
        BaseModel baseModel = BeanFactory.getBean(BaseModel.class);
        if (baseModel instanceof Sort) {
            Sort sort = Sort.class.cast(baseModel);
            if (!Validator.isEmpty(sort))
                sort.setSort(0);
        }
        baseModel.setCreatedAt(new Date());
        baseModel.setUpdatedAt(new Date());
        baseModel.setRemark("");
        baseModel.setDomain("0");
        baseModel.setValidFlag(0);
        //baseModel.setSort(0);
        return baseModel;
    }

    /*private static String getFirstUppercase(String normal) {
        return normal.toString().substring(0, 1).toUpperCase() + normal.toString().substring(1);
    }*/

    private static <T extends Model> Class<Model> getJsonClass(T model, String key) {

        String upperCase = Converter.toFirstUpperCase(key);
        try {
            return (Class<Model>) model.getClass().getMethod("get" + upperCase).getReturnType();
        } catch (NoSuchMethodException e) {
            Logger.warn(e, "设置Model[{}]属性[{}]值时发生异常！", model.getClass(), key);
        }
        return null;
    }

    private static <T extends Model> void setValue(T model, String key, Object value) {
        try {
            String upperCase = Converter.toFirstUpperCase(key);
            model.getClass().getDeclaredMethod("set" + upperCase, model.getClass().getDeclaredField(key.toString()).getType()).invoke(model,
                    convert(model.getClass().getDeclaredField(key.toString()).getType(), value));
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Logger.warn(e, "设置Model[{}]属性[{}]值时发生异常！", model.getClass(), key);
        }
    }

    private static Object convert(Class<?> type, Object value) {
        if (value == null || type.isInstance(value))
            return value;

        if (String.class.equals(type))
            return Converter.toString(value);

        if (int.class.equals(type) || Integer.class.equals(type))
            return Converter.toInt(value);

        if (long.class.equals(type) || Long.class.equals(type))
            return Converter.toLong(value);

        if (float.class.equals(type) || Float.class.equals(type))
            return Converter.toFloat(value);

        if (double.class.equals(type) || Double.class.equals(type))
            return Converter.toDouble(value);

        if (java.sql.Date.class.equals(type) || Timestamp.class.equals(type)) {
            java.util.Date date = Converter.toDate(value.toString());
            if (date == null)
                return null;

            if (java.sql.Date.class.equals(type))
                return new java.sql.Date(date.getTime());

            return new Timestamp(date.getTime());
        }

        return value;
    }

    public static Object format(String format, Object value) {
        if (Validator.isEmpty(format))
            return value;

        if (format.startsWith("number.")) {
            int[] ns = Converter.toInts(format.substring(7));
            String v = (String) value;
            StringBuilder sb = new StringBuilder().append(v);
            int indexOf = v.indexOf('.');
            int point = indexOf == -1 ? 0 : (v.length() - indexOf - 1);
            for (int i = point; i < ns[0]; i++)
                sb.append('0');
            if (indexOf > -1) {
                if (point > ns[0])
                    sb.delete(indexOf + ns[0] + 1, sb.length());
                sb.deleteCharAt(indexOf);
            }

            return sb.toString();
        }

        return value;
    }


    /**
     * 获取实体中文名称
     *
     * @param clazz 实体的类型
     * @param <T>
     * @return
     */
    public static <T extends Model> String getCNName(Class<T> clazz) {
        return getCNName(clazz, "");
    }

    /**
     * 获取实体中文名称
     *
     * @param clazz 实体的类型
     * @param <T>
     * @return
     */
    public static <T extends Model> String getCNName(Class<T> clazz, String name) {
        if (clazz == null)
            return null;
        ModelTables modelTables = BeanFactory.getBean(ModelTables.class);
        ModelTable modelTable = modelTables.get(clazz);
        Map<String, Property> properties = modelTable.getProperties();
        String key = "";
        String className = clazz.getName();
        if (Validator.isEmpty(name))
            key = className;
        else
            key = String.join(".", className, name);

        Property property = properties.get(key);
        return property == null ? "" : property.name();
    }
}
