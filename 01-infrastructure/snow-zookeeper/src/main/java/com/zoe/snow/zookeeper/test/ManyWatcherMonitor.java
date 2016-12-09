package com.zoe.snow.zookeeper.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ManyWatcherMonitor
 *
 * @author Dai Wenqing
 * @date 2016/1/22
 */
public class ManyWatcherMonitor implements Runnable {
    public static void main(String[] args) throws Exception {
        ManyWatcherMonitor manyWatcherMonitor = new ManyWatcherMonitor();
        new Thread(manyWatcherMonitor).start();
    }

    public void run() {
        /*
         * 验证过程如下： 1、验证一个节点X上使用exist方式注册的多个监听器（ManyWatcherOne、ManyWatcherTwo），
         * 在节点X发生create事件时的事件通知情况
         * 2、验证一个节点Y上使用getDate方式注册的多个监听器（ManyWatcherOne、ManyWatcherTwo），
         * 在节点X发生create事件时的事件通知情况
         */
        // 默认监听：注册默认监听是为了让None事件都由默认监听处理，
        // 不干扰ManyWatcherOne、ManyWatcherTwo的日志输出
        ManyWatcherDefault watcherDefault = new ManyWatcherDefault();
        ZooKeeper zkClient = null;
        try {
            zkClient = new ZooKeeper("192.168.2.39:2181", 120000, watcherDefault);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        // 默认监听也可以使用register方法注册
        // zkClient.register(watcherDefault);

        // 1、========================================================
        System.out.println("=================以下是第一个实验");
        String path = "/zk/register";
        ManyWatcherOne watcherOneX = new ManyWatcherOne(zkClient, path);
        ManyWatcherTwo watcherTwoX = new ManyWatcherTwo(zkClient, path);
        // 注册监听，注意，这里两次exists方法的执行返回都是null，因为“X”节点还不存在
        try {
            // zkClient.getChildren("/zk",true);
            // zkClient.exists(path, watcherOneX);
            // zkClient.exists(path, watcherTwoX);
            // 创建"X"节点，为了简单起见，我们忽略权限问题。
            // 并且创建一个临时节点，这样重复跑代码的时候，不用去server上手动删除)
            // zkClient.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
            // CreateMode.EPHEMERAL);
            zkClient.getChildren(path, watcherOneX);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // 下面这段代码可以忽略，是为了观察zk的原理。让守护线程保持不退出
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }

    /**
     * 这是默认的watcher实现。
     *
     * @author yinwenjie
     */
    class ManyWatcherDefault implements Watcher {
        /**
         * 日志
         */
        // private Log LOGGER = LogFactory.getLog(ManyWatcherDefault.class);

        public void process(WatchedEvent event) {
            Event.KeeperState keeperState = event.getState();
            Event.EventType eventType = event.getType();
            System.out.println("=========默认监听到None事件：keeperState = " + keeperState + "  :  eventType = " + eventType);
        }
    }

    /**
     * 这是第一种watcher
     *
     * @author yinwenjie
     */
    class ManyWatcherOne implements Watcher {
        List<String> nodes = new ArrayList<>();
        List<String> newNodes = new ArrayList<>();
        /**
         * 日志
         */
        // private Log LOGGER = LogFactory.getLog(ManyWatcherOne.class);

        private ZooKeeper zkClient;
        /**
         * 被监控的znode地址
         */
        private String watcherPath;
        public ManyWatcherOne(ZooKeeper zkClient, String watcherPath) {
            this.zkClient = zkClient;
            this.watcherPath = watcherPath;
        }

        public void process(WatchedEvent event) {
            List<String> tmpNode = null;

            try {
                this.zkClient.getChildren(watcherPath, this);
                tmpNode = zkClient.getChildren(event.getPath(), true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Event.KeeperState keeperState = event.getState();
            Event.EventType eventType = event.getType();
            // 这个属性是发生事件的path
            String eventPath = event.getWrapper().getPath();
            String newNode = "";
            if (tmpNode != null) {
                tmpNode.forEach(c -> {
                    if (!nodes.contains(c)) {
                        nodes.add(c);
                        newNodes.add(c);
                        try {
                            String data = new String(zkClient.getData(eventPath + "/" + c, true, null));
                            System.out.println("新增了服务器，服器地址Ip:" + data);
                        } catch (KeeperException | InterruptedException e) {

                        }

                    }
                });
            }

            // System.out.println("新增了服务：" + String.join("###", newNodes));
        }
    }

    /**
     * 这是第二种watcher
     *
     * @author yinwenjie
     */
    class ManyWatcherTwo implements Watcher {
        /**
         * 日志
         */
        // private Log LOGGER = LogFactory.getLog(ManyWatcherOne.class);

        private ZooKeeper zkClient;

        /**
         * 被监控的znode地址
         */
        private String watcherPath;

        public ManyWatcherTwo(ZooKeeper zkClient, String watcherPath) {
            this.zkClient = zkClient;
            this.watcherPath = watcherPath;
        }

        public void process(WatchedEvent event) {
            try {
                this.zkClient.exists(this.watcherPath, this);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Event.KeeperState keeperState = event.getState();
            Event.EventType eventType = event.getType();
            // 这个属性是发生事件的path
            String eventPath = event.getPath();

            System.out.println("=========ManyWatcherTwo监听到" + eventPath + "地址发生事件：" + "keeperState = " + keeperState + "  :  eventType = " + eventType);
        }
    }
}
