package com.zoe.snow.mq.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * MessageErrorHandler
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public class MessageErrorHandler implements ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        logger.error("RabbitMQ happen a error:" + t.getMessage(), t);
    }

}
