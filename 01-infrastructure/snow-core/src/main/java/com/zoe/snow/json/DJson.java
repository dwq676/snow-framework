package com.zoe.snow.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * DJson
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/21
 */
public class DJson {
    /**
     * 将Model转化为JSON格式的数据。转化时将调用所有get方法输出属性值。
     *
     * @param model Model实例。
     * @return JSON数据。
     */
    public static <T> JSONObject toJson(T model) {
        if (model == null)
            return null;

        JSONObject object = new JSONObject();
        Method[] methods = model.getClass().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.length() < 3)
                continue;

            if (name.startsWith("get")) {
                String propertyName = name.substring(3);
                Jsonable jsonable = method.getAnnotation(Jsonable.class);
                if (jsonable != null)
                    if (jsonable.hidden())
                        continue;

                try {
                    object.put(propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1), parseJson(method.invoke(model), jsonable));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    Logger.warn(e, "获取Model[{}]属性[{}]值时发生异常！", model, name);
                }
            }
        }
        return object;
    }

    public static <T extends Serializable> JSONArray toJson(Collection<T> list) {
        JSONArray jsonArray = new JSONArray();
        if (list != null) {
            if (list.size() > 0) {
                list.forEach(c -> {
                    jsonArray.add(toJson(c));
                });
            }
        }
        return jsonArray;
    }

    @SuppressWarnings("unchecked")
    protected static <T extends Serializable> Object parseJson(Object value, Jsonable jsonable) {
        if (value == null)
            return null;

        if (value instanceof Serializable)
            return toJson((T) value);

        if (value instanceof Collection) {
            if (Validator.isEmpty(value))
                return null;

            JSONArray array = new JSONArray();
            for (Object object : (Collection<?>) value)
                array.add(parseJson(object, jsonable));

            return array;
        }

        if (value instanceof Map) {
            if (Validator.isEmpty(value))
                return null;
            JSONObject jsonObject = new JSONObject();
            for (Object key : ((Map) value).keySet()) {
                Object v = ((Map) value).get(key);
                jsonObject.put(key.toString(), parseJson(v, jsonable));
            }
            return jsonObject;

        }

        String format = jsonable.format();
        if (!Validator.isEmpty(format)) {
            if (format.startsWith("number.")) {
                int[] ns = Converter.toInts(format.substring(7));
                return Converter.toString(value, ns[0], ns[1]);
            }
        }

        return Converter.toString(value);
    }


    public static <T extends Serializable> List<T> parseJsonArray(String json, Class<T> modelClass) {
        List<T> list = new ArrayList<T>();
        JSONArray jsonArray = JSONArray.parseArray(json);
        try {
            if (jsonArray != null) {
                jsonArray.forEach(c -> {
                    T t = parseJson(c.toString(), modelClass);
                    list.add(t);
                });
            }
        } catch (Exception e) {
            Logger.error(e, "将Json数组转换成List失败");
        }
        return list;

    }

    /**
     * 将JSON对象转化为Model对象。
     *
     * @param json       JSON对象。
     * @param modelClass Model类。
     * @return Model对象；如果转化失败则返回null。
     */
    public static <T extends Serializable> T parseJson(String json, Class<T> modelClass) {
        if (json == null || modelClass == null)
            return null;

        /*T model = BeanFactory.getBean(modelClass);
        if (json.has("id"))
            model.setId(json.getString("id"));
        */
        T model = null;
        try {
            model = modelClass.newInstance();
        } catch (Exception e) {
        }
        if (model == null)
            return null;
        Map<String, Class> m = new HashMap<>();
        for (Field f : model.getClass().getDeclaredFields()) {
            Class fieldClazz = f.getType(); // 得到field的class及类型全路径

            if (fieldClazz.isPrimitive())
                continue;
            if (fieldClazz.getName().startsWith("java.lang"))
                continue; // getName()返回field的类型全路径；
            if (fieldClazz.isAssignableFrom(List.class)) // 【2】
            {
                Type fc = f.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
                if (fc == null)
                    continue;
                if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
                {
                    ParameterizedType pt = (ParameterizedType) fc;
                    Class genericClazz = (Class) pt.getActualTypeArguments()[0]; // 【4】
                    // 得到泛型里的class类型对象。
                    m.put(f.getName(), genericClazz);
                }
            }
        }
        /*JSONObject jsonObject = JSONObject.parseObject(json);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        jsonConfig.setRootClass(modelClass);
        jsonConfig.setClassMap(m);*/
        return (T) JSONObject.parseObject(json, modelClass);
    }
}
