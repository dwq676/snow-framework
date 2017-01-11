package com.zoe.snow.delivery;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 服务工厂
 * 用于获取相应的服务
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2016/12/29
 */
@Component("snow.service.bean.factory")
public class ServiceBeanFactory {
    private Collection<ServiceBean> serviceBeans;
}
