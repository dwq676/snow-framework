package com.zoe.snow.log;

/**
 * LogType
 *
 * @author Dai Wenqing
 * @date 2016/10/9
 */
public enum BasicLogType implements LogType {
    //public static final String SYSTEM = "system";
    /**
     * 类型在日志内容的字段里
     */
    InContent("log_type"),

    System("system"),

    User("user");

    protected String type;

    BasicLogType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*@Component("snow.log.type.model")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public class LogTypeModel {
        private String type;

        public LogTypeModel(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }*/

}
