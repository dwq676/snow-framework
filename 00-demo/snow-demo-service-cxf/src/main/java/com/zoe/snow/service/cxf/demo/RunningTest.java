package com.zoe.snow.service.cxf.demo;

import org.springframework.stereotype.Component;

/**
 * RunningTest
 *
 * @author Dai Wenqing
 * @date 2016/1/18
 */
public class RunningTest implements Runnable {
    int i = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println(i);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }
}
