package com.zoe.snow.mq.rabbit;

import com.zoe.snow.bean.ContextRefreshedListener;
import com.zoe.snow.conf.RabbitMqConfiguration;
import com.zoe.snow.mq.RabbitConsumer;
import com.zoe.snow.mq.Rabbitmq;
import com.zoe.snow.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

/**
 * RabbitmqImpl
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
@Component("snow.mq.rabbit")
public class RabbitmqImpl implements Rabbitmq, ContextRefreshedListener {
    @Autowired
    private RabbitMqConfiguration config;
    private DefaultEventController controller;
    private EventTemplate eventTemplate;
    private Logger logger = LoggerFactory.getLogger(RabbitmqImpl.class);
    @Autowired(required = false)
    private Set<RabbitConsumer> rabbitConsumers;

    protected void init() throws IOException {
        controller = DefaultEventController.getInstance(config);
        eventTemplate = controller.getEventTemplate();
        if (!Validator.isEmpty(rabbitConsumers)) {
            rabbitConsumers.forEach(rabbitConsumer -> {
                controller.add(config.getDefaultRabbitmq(), config.getDefaultRabbitmqExchange(), rabbitConsumer);
            });
        }
        controller.start();
    }

    @Override
    public void sendMessage(String msg) throws SendRefuseException {
        eventTemplate.send(config.getDefaultRabbitmq(), config.getDefaultRabbitmqExchange(), msg);
    }

    @Override
    public void send(Object obj) throws SendRefuseException {
        eventTemplate.send(config.getDefaultRabbitmq(), config.getDefaultRabbitmqExchange(), obj, new HessionCodecFactory());
    }

    @Override
    public int getContextRefreshedSort() {
        return 8;
    }

    @Override
    public void onContextRefreshed() {
        try {
            if (config.getRabbitmqSwitch())
                init();
        } catch (Exception ex) {
            logger.error("rabbitmq 初始化发现了异常", ex);
        }
    }
}
