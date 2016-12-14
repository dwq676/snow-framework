package com.zoe.snow.log.appender.elastic;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.zoe.snow.Global;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.dao.elastic.ElasticDao;
import com.zoe.snow.log.BasicLogType;
import com.zoe.snow.util.Validator;
import net.sf.json.JSONObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ElasticAppender
 *
 * @author Dai Wenqing
 * @date 2016/10/9
 */
public class ElasticAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private Queue<Object> queue = new ConcurrentLinkedQueue<>();

    @Override
    protected void append(ILoggingEvent eventObject) {
        String msg = eventObject.getFormattedMessage();
        /*StackTraceElement[] stackTraceElements = eventObject.getCallerData();
        stackTraceElements[0].getClassName();*/
        //eventObject.get
        String content = "";
        String classNameSpilt = "###";
        //默认为系统日志
        String type = BasicLogType.System.getType();
        String className = "";
        if (Validator.isEmpty(msg))
            return;
        if (msg.contains(Global.split)) {
            String[] msgs = msg.split(classNameSpilt);
            if (msgs.length > 1) {
                msg = msgs[1];
                className = msgs[0];
            } else {
                msg = msgs[0];
            }
            String[] contents = msg.split(Global.split);
            if (contents.length > 1) {
                type = contents[0];
                content = contents[1];
            } else
                content = contents[0];
        } else {
            content = msg;
        }
        JSONObject jsonObject = new JSONObject();


        try {
            JSONObject jo = JSONObject.fromObject(content);
            jsonObject.put("message", "");
            jsonObject.put("detail", jo);
        } catch (Exception e) {
            content = className + classNameSpilt + content;
            jsonObject.put("message", content);
        }

        if (Global.elasticReady) {
            ElasticDao elasticDao = BeanFactory.getBean(ElasticDao.class);
            while (!queue.isEmpty()) {
                elasticDao.save("logger", type, queue.poll().toString());
            }
            elasticDao.save("logger", type, jsonObject.toString());
        } else {
            queue.add(jsonObject);
        }
    }

}
