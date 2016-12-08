package com.zoe.snow.service.cxf.demo;

import org.springframework.stereotype.Component;

/**
 * ThreadRunning
 *
 * @author Dai Wenqing
 * @date 2016/1/18
 */
@Component("service.cxf.demo.running")
public class ThreadRunning {
    public ThreadRunning() {
        new Thread(new RunningTest()).start();
    }
}
