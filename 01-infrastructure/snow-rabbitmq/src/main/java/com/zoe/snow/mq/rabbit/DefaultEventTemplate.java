package com.zoe.snow.mq.rabbit;

import com.zoe.snow.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;

import java.io.IOException;

/**
 * DefaultEventTemplate
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public class DefaultEventTemplate implements EventTemplate {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEventTemplate.class);
    private AmqpTemplate eventAmqpTemplate;
    private CodecFactory defaultCodecFactory;
    private DefaultEventController eec;

    public DefaultEventTemplate(AmqpTemplate eopAmqpTemplate, CodecFactory defaultCodecFactory, DefaultEventController eec) {
        this.eventAmqpTemplate = eopAmqpTemplate;
        this.defaultCodecFactory = defaultCodecFactory;
        this.eec = eec;
    }

    public DefaultEventTemplate(AmqpTemplate eopAmqpTemplate, CodecFactory defaultCodecFactory) {
        this.eventAmqpTemplate = eopAmqpTemplate;
        this.defaultCodecFactory = defaultCodecFactory;
    }

    @Override
    public void send(String routeKey, String exchangeName, Object eventContent) throws SendRefuseException {
        this.send(routeKey, exchangeName, eventContent, defaultCodecFactory);
    }

    @Override
    public void send(String routeKey, String exchangeName, Object eventContent, CodecFactory codecFactory) throws SendRefuseException {
        if (Validator.isEmpty(routeKey) || Validator.isEmpty(exchangeName)) {
            throw new SendRefuseException("queueName exchangeName can not be empty.");
        }

        byte[] eventContentBytes = null;
        if (codecFactory == null) {
            if (eventContent == null) {
                logger.warn("Find eventContent is null,are you sure...");
            } else {
                throw new SendRefuseException("codecFactory must not be null ,unless eventContent is null");
            }
        } else {
            try {
                eventContentBytes = codecFactory.serialize(eventContent);
            } catch (IOException e) {
                throw new SendRefuseException(e);
            }
        }

        // 构造成Message
        EventMessage msg = new EventMessage(routeKey, exchangeName, eventContentBytes);
        try {
            eventAmqpTemplate.convertAndSend(exchangeName, routeKey, msg);
        } catch (AmqpException e) {
            logger.error("send event fail. Event Message : [" + eventContent + "]", e);
            throw new SendRefuseException("send event fail", e);
        }
    }

    @Override
    public Object receive(String queueName, long timeoutMillis) {
        return eventAmqpTemplate.receiveAndConvert(queueName, timeoutMillis);
    }
}
