package com.zoe.snow.mq;

/**
 * Rabbitmq 消息订阅类，凡需要订阅消息的都实现此接口
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public interface RabbitConsumer {
    public void process(Object e);
}
