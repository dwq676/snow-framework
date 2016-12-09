package com.zoe.snow.zookeeper.test;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * TestManyWatcher
 *
 * @author Dai Wenqing
 * @date 2016/1/15
 */
public class TestManyWatcher implements  Runnable {
/*    static {
        try {
            Log4jConfigurer.initLogging("classpath:log4j.properties");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot Initialize log4j");
            System.exit(-1);
        }
    }*/

    /**
     * 日志
     */
    //private static final Log LOGGER = LogFactory.getLog(TestManyWatcher.class);

    public static void main(String[] args) throws Exception {
        TestManyWatcher testManyWatcher = new TestManyWatcher();
        new Thread(testManyWatcher).start();
    }

    public void run() {
        /*
         * 验证过程如下：
         * 1、验证一个节点X上使用exist方式注册的多个监听器（ManyWatcherOne、ManyWatcherTwo），
         *      在节点X发生create事件时的事件通知情况
         * 2、验证一个节点Y上使用getDate方式注册的多个监听器（ManyWatcherOne、ManyWatcherTwo），
         *      在节点X发生create事件时的事件通知情况
         * */
        //默认监听：注册默认监听是为了让None事件都由默认监听处理，
        //不干扰ManyWatcherOne、ManyWatcherTwo的日志输出
        ManyWatcherDefault watcherDefault = new ManyWatcherDefault();
        ZooKeeper zkClient = null;
        try {
            zkClient = new ZooKeeper("192.168.2.39:2181", 120000, watcherDefault);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        //默认监听也可以使用register方法注册
        //zkClient.register(watcherDefault);

        //1、========================================================
        System.out.println("=================注册服务测试=============================");
        String path = "/zk/register/4";
        ManyWatcherOne watcherOneX = new ManyWatcherOne(zkClient , path);
        ManyWatcherTwo watcherTwoX = new ManyWatcherTwo(zkClient , path);
        //注册监听，注意，这里两次exists方法的执行返回都是null，因为“X”节点还不存在
        try {
            //zkClient.getChildren("/zk",true);
            /*zkClient.exists(path, watcherOneX);
            zkClient.exists(path, watcherTwoX);*/

            //创建"X"节点，为了简单起见，我们忽略权限问题。
            //并且创建一个临时节点，这样重复跑代码的时候，不用去server上手动删除)
            zkClient.create(path, "服务1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            //zkClient.getChildren(path,watcherTwoX);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        //TODO 注意观察日志，根据原理我们猜测理想情况下ManyWatcherTwo和ManyWatcherOne都会被通知。

        //2、========================================================
       /* System.out.println("=================以下是第二个实验");
        path = "/Y";
        ManyWatcherOne watcherOneY = new ManyWatcherOne(zkClient , path);
        ManyWatcherTwo watcherTwoY = new ManyWatcherTwo(zkClient , path);
        //注册监听，注意，这里使用两次getData方法注册监听，"Y"节点目前并不存在
        try {
            zkClient.getData(path, watcherOneY, null);
            zkClient.getData(path, watcherTwoY, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //TODO 注意观察日志，因为"Y"节点不存在，所以getData就会出现异常。watcherOneY、watcherTwoY的注册都不起任何作用。
        //然后我们在报了异常的情况下，创建"Y"节点，根据原理，不会有任何watcher响应"Y"节点的create事件
        try {
            zkClient.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }*/

        //下面这段代码可以忽略，是为了观察zk的原理。让守护线程保持不退出
        synchronized(this) {
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
        //private Log LOGGER = LogFactory.getLog(ManyWatcherDefault.class);

        public void process(WatchedEvent event) {
            Event.KeeperState keeperState = event.getState();
            Event.EventType eventType = event.getType();
            System.out.println("=========默认监听到None事件：keeperState = "
                    + keeperState + "  :  eventType = " + eventType);
        }
    }

    /**
     * 这是第一种watcher
     *
     * @author yinwenjie
     */
    class ManyWatcherOne implements Watcher {
        /**
         * 日志
         */
        //private Log LOGGER = LogFactory.getLog(ManyWatcherOne.class);

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
            try {
                this.zkClient.exists(this.watcherPath, this);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Event.KeeperState keeperState = event.getState();
            Event.EventType eventType = event.getType();
            //这个属性是发生事件的path
            String eventPath = event.getPath();

            System.out.println("=========ManyWatcherOne监听到" + eventPath + "地址发生事件："
                    + "keeperState = " + keeperState + "  :  eventType = " + eventType);
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
        //private Log LOGGER = LogFactory.getLog(ManyWatcherOne.class);

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
            //这个属性是发生事件的path
            String eventPath = event.getPath();

            System.out.println("=========ManyWatcherTwo监听到" + eventPath + "地址发生事件："
                    + "keeperState = " + keeperState + "  :  eventType = " + eventType);
        }
    }
}
