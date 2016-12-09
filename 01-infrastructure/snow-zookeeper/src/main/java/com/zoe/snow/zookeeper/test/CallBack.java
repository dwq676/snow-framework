package com.zoe.snow.zookeeper.test;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

/**
 * CallBack
 *
 * @author Dai Wenqing
 * @date 2016/1/12
 */
public class CallBack implements AsyncCallback.StatCallback {
    int a = 0;

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.printf("收到节点变化通知：" + "#####" + rc + "#####" + ",#####" + ctx);
        a++;
    }
}
