package com.zoe.snow.mq;

import com.zoe.snow.mq.rabbit.SendRefuseException;

/**
 * Rabbitmq
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */

public interface Rabbitmq {
    void sendMessage(String msg) throws SendRefuseException;
    void send(Object object) throws SendRefuseException;
}
