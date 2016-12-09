package com.zoe.snow.mq.rabbit;

import com.zoe.snow.mq.RabbitConsumer;

import java.util.Map;

/**
 * EventController
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public interface EventController {
    /**
     * 控制器启动方法
     */
    void start();

    /**
     * 获取发送模版
     */
    EventTemplate getEventTemplate();

    /**
     * 绑定消费程序到对应的exchange和queue
     */
    EventController add(String queueName, String exchangeName, RabbitConsumer rabbitConsumer);

    /*in map, the key is queue name, but value is exchange name*/
    EventController add(Map<String,String> bindings, RabbitConsumer rabbitConsumer);
}
