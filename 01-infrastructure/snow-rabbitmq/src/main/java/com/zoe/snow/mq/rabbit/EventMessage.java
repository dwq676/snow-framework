package com.zoe.snow.mq.rabbit;

import java.io.Serializable;
import java.util.Arrays;

/**
 * EventMessage
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public class EventMessage implements Serializable {
    private String queueName;

    private String exchangeName;

    private byte[] eventData;

    public EventMessage(String queueName, String exchangeName, byte[] eventData) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.eventData = eventData;
    }

    public EventMessage() {
    }

    public String getQueueName() {
        return queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public byte[] getEventData() {
        return eventData;
    }

    @Override
    public String toString() {
        return "EopEventMessage [queueName=" + queueName + ", exchangeName="
                + exchangeName + ", eventData=" + Arrays.toString(eventData)
                + "]";
    }
}
