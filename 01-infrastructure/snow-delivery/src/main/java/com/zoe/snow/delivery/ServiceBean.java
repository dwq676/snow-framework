package com.zoe.snow.delivery;

import org.springframework.stereotype.Component;

/**
 * ServiceBean
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2016/12/29
 */
@Component("snow.delivery.service.bean")
public class ServiceBean<T> {
    private String uri;

    /*private ServiceBean(String uri) {
        this.uri = uri;
    }*/

    public Object execute() {
        return null;
    }
}
