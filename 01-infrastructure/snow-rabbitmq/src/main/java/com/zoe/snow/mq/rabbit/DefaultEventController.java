package com.zoe.snow.mq.rabbit;

import com.zoe.snow.conf.RabbitMqConfiguration;
import com.zoe.snow.mq.RabbitConsumer;
import com.zoe.snow.util.Validator;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 和rabbitmq通信的控制器，主要负责：
 * <p>
 * 1、和rabbitmq建立连接
 * </p>
 * <p>
 * 2、声明exChange和queue以及它们的绑定关系
 * </p>
 * <p>
 * 3、启动消息监听容器，并将不同消息的处理者绑定到对应的exchange和queue上
 * </p>
 * <p>
 * 4、持有消息发送模版以及所有exchange、queue和绑定关系的本地缓存
 * </p>
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public class DefaultEventController implements EventController {
    private static DefaultEventController defaultEventController;
    private CachingConnectionFactory rabbitConnectionFactory;
    private RabbitMqConfiguration config;
    private RabbitAdmin rabbitAdmin;
    private CodecFactory defaultCodecFactory = new HessionCodecFactory();
    // rabbitMQ msg listener container
    private SimpleMessageListenerContainer msgListenerContainer;
    private MessageAdapterHandler msgAdapterHandler = new MessageAdapterHandler();
    // 直接指定
    private MessageConverter serializerMessageConverter = new SerializerMessageConverter();
    // queue cache, key is exchangeName
    private Map<String, DirectExchange> exchanges = new HashMap<String, DirectExchange>();
    private Map<String, TopicExchange> topicExchangeMap = new HashedMap();
    // queue cache, key is queueName
    private Map<String, Queue> queues = new HashMap<String, Queue>();
    // bind relation of queue to exchange cache, value is exchangeName |
    // queueName
    private Set<String> binded = new HashSet<String>();
    private EventTemplate eventTemplate; // 给App使用的Event发送客户端
    private AtomicBoolean isStarted = new AtomicBoolean(false);

    private DefaultEventController(RabbitMqConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("Config can not be null.");
        }
        this.config = config;
        initRabbitConnectionFactory();
        // 初始化AmqpAdmin
        rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
        // 初始化RabbitTemplate
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setRoutingKey(config.getDefaultRabbitmq());

        declareBinding(config.getDefaultRabbitmqExchange(), config.getDefaultRabbitmq());

        rabbitTemplate.setMessageConverter(serializerMessageConverter);
        eventTemplate = new DefaultEventTemplate(rabbitTemplate, defaultCodecFactory, this);
    }

    public synchronized static DefaultEventController getInstance(RabbitMqConfiguration config) {
        if (defaultEventController == null) {
            defaultEventController = new DefaultEventController(config);
        }
        return defaultEventController;
    }

    /**
     * 初始化rabbitmq连接
     */
    private void initRabbitConnectionFactory() {
        rabbitConnectionFactory = new CachingConnectionFactory();
        rabbitConnectionFactory.setHost(config.getServerHost());
        rabbitConnectionFactory.setChannelCacheSize(config.getEventMsgProcessNum());
        rabbitConnectionFactory.setPort(config.getRabbitMQPort());
        rabbitConnectionFactory.setUsername(config.getRabbitMQUsername());
        rabbitConnectionFactory.setPassword(config.getRabbitMQPassword());
        if (!Validator.isEmpty(config.getVirtualHost())) {
            rabbitConnectionFactory.setVirtualHost(config.getVirtualHost());
        }
    }

    /**
     * 注销程序
     */
    public synchronized void destroy() throws Exception {
        if (!isStarted.get()) {
            return;
        }
        msgListenerContainer.stop();
        eventTemplate = null;
        rabbitAdmin = null;
        rabbitConnectionFactory.destroy();
    }

    @Override
    public void start() {
        if (isStarted.get()) {
            return;
        }
        Set<String> mapping = msgAdapterHandler.getAllBinding();
        for (String relation : mapping) {
            String[] relaArr = relation.split("\\|");
            declareBinding(relaArr[1], relaArr[0]);
        }
        initMsgListenerAdapter();
        isStarted.set(true);
    }

    /**
     * 初始化消息监听器容器
     */
    private void initMsgListenerAdapter() {
        MessageListener listener = new MessageListenerAdapter(msgAdapterHandler, serializerMessageConverter);
        msgListenerContainer = new SimpleMessageListenerContainer();
        msgListenerContainer.setConnectionFactory(rabbitConnectionFactory);
        msgListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        msgListenerContainer.setMessageListener(listener);
        msgListenerContainer.setErrorHandler(new MessageErrorHandler());
        msgListenerContainer.setPrefetchCount(config.getPrefetchSize()); // 设置每个消费者消息的预取值
        msgListenerContainer.setConcurrentConsumers(config.getEventMsgProcessNum());
        msgListenerContainer.setTxSize(config.getPrefetchSize());// 设置有事务时处理的消息数
        msgListenerContainer.setQueues(queues.values().toArray(new Queue[queues.size()]));
        msgListenerContainer.setQueueNames(config.getDefaultRabbitmq());
        // msgListenerContainer
        msgListenerContainer.start();
    }

    @Override
    public EventTemplate getEventTemplate() {
        return eventTemplate;
    }

    @Override
    public EventController add(String queueName, String exchangeName, RabbitConsumer eventProcesser) {
        return add(queueName, exchangeName, eventProcesser, defaultCodecFactory);
    }

    public EventController add(String queueName, String exchangeName, RabbitConsumer eventProcesser, CodecFactory codecFactory) {
        msgAdapterHandler.add(queueName, exchangeName, eventProcesser, defaultCodecFactory);
        if (isStarted.get()) {
            initMsgListenerAdapter();
        }
        return this;
    }

    @Override
    public EventController add(Map<String, String> bindings, RabbitConsumer eventProcesser) {
        return add(bindings, eventProcesser, defaultCodecFactory);
    }

    public EventController add(Map<String, String> bindings, RabbitConsumer eventProcesser, CodecFactory codecFactory) {
        for (Map.Entry<String, String> item : bindings.entrySet())
            msgAdapterHandler.add(item.getKey(), item.getValue(), eventProcesser, codecFactory);
        return this;
    }

    /**
     * exchange和queue是否已经绑定
     */
    protected boolean beBinded(String exchangeName, String queueName) {
        return binded.contains(exchangeName + "|" + queueName);
    }

    /**
     * 声明exchange和queue已经它们的绑定关系
     */
    protected synchronized void declareBinding(String exchangeName, String queueName) {
        String bindRelation = exchangeName + "|" + queueName;
        if (binded.contains(bindRelation))
            return;

        boolean needBinding = false;
        TopicExchange exchange = topicExchangeMap.get(exchangeName);

        // TopicExchange topicExchange
        if (exchange == null) {
            exchange = new TopicExchange(exchangeName, true, false, null);
            // exchanges.put(exchangeName, exchange);
            topicExchangeMap.put(exchangeName, exchange);
            rabbitAdmin.declareExchange(exchange);// 声明exchange
            needBinding = true;
        }

        Queue queue = queues.get(queueName);
        if (queue == null) {
            queue = new Queue(queueName, true, false, false);
            queues.put(queueName, queue);
            rabbitAdmin.declareQueue(queue); // 声明queue
            needBinding = true;
        }

        if (needBinding) {
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(queueName);// 将queue绑定到exchange
            rabbitAdmin.declareBinding(binding);// 声明绑定关系
            binded.add(bindRelation);
        }
    }
}
