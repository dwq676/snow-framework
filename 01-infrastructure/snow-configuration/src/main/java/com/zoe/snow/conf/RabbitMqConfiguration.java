package com.zoe.snow.conf;

/**
 * RabbitMqConfiguration
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public interface RabbitMqConfiguration {

    default int getRabbitMQPort() {
        return 5672;
    };

    default boolean getRabbitmqSwitch() {
        return false;
    }

    default String getDefaultRabbitmq() {
        return "##RABBITMQ_DEFALUT";
    }

    default String getDefaultRabbitmqExchange() {
        return "##RABBITMQ_EXCHANGE_DIRECT";
    }

    default String getRabbitMQUsername() {
        return "guest";
    };

    default String getRabbitMQPassword() {
        return "guest";
    };

    default int getProcessThreadNum() {
        return Runtime.getRuntime().availableProcessors() * 2;
    }

    default int getPrefetchSize() {
        return 1;
    }

    String getServerHost();

    String getVirtualHost();

    /**
     * 和rabbitmq建立连接的超时时间
     */
    default int getConnectionTimeout() {
        return 0;
    }

    /**
     * 事件消息处理线程数，默认是 CPU核数 * 2
     */
    default int getEventMsgProcessNum() {
        return 2;
    }
}
