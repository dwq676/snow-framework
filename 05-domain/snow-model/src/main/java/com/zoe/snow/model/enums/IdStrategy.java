package com.zoe.snow.model.enums;

/**
 * IdStrategy
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/19
 */
public class IdStrategy {
    /**
     * 递增
     */
    public static final String Increment = "increment";
    /**
     * 标识
     */
    public static final String Identity = "identity";

    /**
     * 序列
     */
    public static final String Sequence = "sequence";

    /**
     * 高低位
     */
    public static final String Hilo = "hilo";

    /**
     * 使用序列的高低位
     */
    public static final String Seqhilo = "seqhilo";

    public static final String UUID = "uuid";

    public static final String GUID = "guid";

    /**
     * 本地
     */
    public static final String Native = "native";

    /**
     * 自行分配
     */
    public static final String Assigned = "assigned";

    /**
     * 通过数据库触发器选择一些唯一主键的行并返回主键值来分配一个主键
     */
    public static final String select = "select";

}
