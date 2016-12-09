package com.zoe.snow.mq.rabbit;

import org.springframework.amqp.AmqpException;

/**
 * EventTemplate
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public interface EventTemplate {
    void send(String routeKey, String exchangeName, Object eventContent) throws SendRefuseException;

    void send(String routeKey, String exchangeName, Object eventContent, CodecFactory codecFactory) throws SendRefuseException;

    /*
     * @param queueName the queue to receive from
     * 
     * @param timeoutMillis how long to wait before giving up. Zero value means
     * the method will return {@code null} immediately if there is no message
     * available. Negative value makes method wait for a message indefinitely.
     * 
     * @return a message or null if the time expires
     * 
     * @throws AmqpException if there is a problem
     * 
     * @since 1.6
     */
    Object receive(String routeKey, long timeoutMillis);
}
