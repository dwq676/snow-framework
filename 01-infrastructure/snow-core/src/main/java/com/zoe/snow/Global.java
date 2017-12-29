package com.zoe.snow;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局变量枚举
 *
 * @author Dai Wenqing
 * @date 2016/10/10
 */
public class Global {
    private static final Map<String, Object> context = new ConcurrentHashMap<>();
    /*
    * application is ready
    */
    public static boolean ready = false;
    public static boolean elasticReady = false;
    /*public static ThreadLocal<Object> user = new ThreadLocal<>();*/
    public static ThreadLocal<String> token = new ThreadLocal<>();
    public static String GLOBAL_DOMAIN = "";

    public static Map<String, Object> getContext() {
        return context;
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

    /**
     * 性别
     */
    public enum SexType {
        Man(0),
        Woman(1);

        private int type;
        private Map<Integer, SexType> map;

        SexType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public SexType get(int type) {
            if (map == null) {
                map = new ConcurrentHashMap<>();
                for (SexType dataType : SexType.values())
                    map.put(dataType.getType(), dataType);
            }
            return map.get(type);
        }
    }

    /**
     * 有效标记
     */
    public enum ValidFlag {
        /**
         * 彻底删除
         */
        Clear(-1),
        /**
         * 有效
         */
        Valid(1),
        /**
         * 已删除
         */
        Delete(0);
        private int type;
        private Map<Integer, ValidFlag> map;

        ValidFlag(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public ValidFlag get(int type) {
            if (map == null) {
                map = new ConcurrentHashMap<>();
                for (ValidFlag dataType : ValidFlag.values())
                    map.put(dataType.getType(), dataType);
            }
            return map.get(type);
        }
    }

    /**
     * -1 = 只查询无效的数据,0=只查询有效数据,1=查询全部数据
     */
    public enum QueryFlag {
        Invalid(-1),
        Valid(0),
        All(1);
        private int type;
        private Map<Integer, QueryFlag> map;

        QueryFlag(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public QueryFlag get(int type) {
            if (map == null) {
                map = new ConcurrentHashMap<>();
                for (QueryFlag dataType : QueryFlag.values())
                    map.put(dataType.getType(), dataType);
            }
            return map.get(type);
        }
    }

    public enum Action {
        Update, Select, Delete, Insert, Execute;
    }

    public enum DataType {
        string, json, xml, yml
    }

    public class Constants {
        public static final String split = "#split#";
        public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        public static final String dateFormat = "yyyy-MM-dd";
        public static final String DOMAIN = "domainId";
        public static final String STRING_NONE = "STRNaN";

        public class auth {
            public static final String IS_ADMIN = "is_admin";
            public static final String IS_SUPER_ADMIN = "is_super_admin";
            public static final String PLATFORM = "appid";
            public static final String ADMIN = "admin";
            public static final String ROOT = "root";
        }
    }

}
