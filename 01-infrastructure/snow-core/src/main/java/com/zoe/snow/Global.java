package com.zoe.snow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局变量枚举
 *
 * @author Dai Wenqing
 * @date 2016/10/10
 */
public class Global {
    public static final String split = "#split#";
    /*
    * elastic启动是否准备好
    */
    public static boolean elasticReady = false;
    public static ThreadLocal<Object> user = new ThreadLocal<>();
    public static ThreadLocal<String> token = new ThreadLocal<>();

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

}
