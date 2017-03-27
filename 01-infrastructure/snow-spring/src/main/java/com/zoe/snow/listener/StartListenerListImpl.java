package com.zoe.snow.listener;

import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 监听器启动列表的实现
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/3/27
 */
@Component("snow.listener.start-list")
public class StartListenerListImpl implements StartListenerList {
    @Value("${snow.listener-list:BeanScan,Cache,DataSourceManage,Elastic,Http,ModelTables,OrmManage,Rabbitmq,RedisCache,SessionManage}")
    private String listenerListValues;

    @Override
    public Collection<String> getListenerList() {
        List<String> listenerList = new ArrayList<>();
        if (!Validator.isEmpty(listenerListValues)) {
            for (String listener : listenerListValues.split(",")) {
                listenerList.add(listener);
            }
        }
        return listenerList;
    }
}
