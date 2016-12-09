package com.zoe.snow.zookeeper.test;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.api.CuratorWatcher;
import com.netflix.curator.framework.state.ConnectionState;
import com.netflix.curator.framework.state.ConnectionStateListener;
import com.netflix.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * CuratorTest
 *
 * @author Dai Wenqing
 * @date 2016/1/15
 */
public class CuratorTest {
    private static Charset charset = Charset.forName("utf-8");
    private CuratorFramework zkTools;
    private ConcurrentSkipListSet watchers = new ConcurrentSkipListSet();


    public CuratorTest() {
        zkTools = CuratorFrameworkFactory
                .builder()
                .connectString("10.0.2.7:2181")
                .namespace("zk")
                .retryPolicy(new RetryNTimes(2000,20000))
                .build();
        zkTools.start();

    }

    public static void main(String[] args) throws Exception {
        CuratorTest test = new CuratorTest();
        test.get();
        test.register();
        Thread.sleep(10000000000L);

    }

    public void addReconnectionWatcher(final String path,final ZookeeperWatcherType watcherType,final CuratorWatcher watcher){
        synchronized (this) {
            if(!watchers.contains(watcher.toString()))//不要添加重复的监听事件
            {
                watchers.add(watcher.toString());
                System.out.println("add new watcher " + watcher);
                zkTools.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                @Override
                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                    System.out.println(newState);
                    if(newState == ConnectionState.LOST){//处理session过期
                        try{
                            if(watcherType == ZookeeperWatcherType.EXITS){
                                zkTools.checkExists().usingWatcher(watcher).forPath(path);
                            }else if(watcherType == ZookeeperWatcherType.GET_CHILDREN){
                                zkTools.getChildren().usingWatcher(watcher).forPath(path);
                            }else if(watcherType == ZookeeperWatcherType.GET_DATA){
                                zkTools.getData().usingWatcher(watcher).forPath(path);
                            }else if(watcherType == ZookeeperWatcherType.CREATE_ON_NO_EXITS){
                                //ephemeral类型的节点session过期了，需要重新创建节点，并且注册监听事件，之后监听事件中，
                                //会处理create事件，将路径值恢复到先前状态
                                Stat stat = zkTools.checkExists().usingWatcher(watcher).forPath(path);
                                if(stat == null){
                                    System.err.println("to create");
                                    zkTools.create()
                                            .creatingParentsIfNeeded()
                                            .withMode(CreateMode.EPHEMERAL)
                                            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                                            .forPath(path);
                                }
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            }
        }
    }

    public void create() throws Exception{
        zkTools.create()//创建一个路径
                .creatingParentsIfNeeded()//如果指定的节点的父节点不存在，递归创建父节点
                .withMode(CreateMode.PERSISTENT)//存储类型（临时的还是持久的）
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//访问权限
                .forPath("/register");//创建的路径
    }

    public void put() throws Exception{
        zkTools.//对路径节点赋值
                setData().
                forPath("/register","hello world".getBytes(Charset.forName("utf-8")));
    }

    public void get() throws Exception{
        String path = "/register";
        ZKWatch watch = new ZKWatch(path);
        byte[] buffer = zkTools.
                getData().
                usingWatcher(watch).forPath(path);
        System.out.println(new String(buffer,charset));
        //添加session过期的监控
        addReconnectionWatcher(path, ZookeeperWatcherType.GET_DATA, watch);
    }

    public void register() throws Exception{

        String ip = InetAddress.getLocalHost().getHostAddress();
        String registerNode = "/zk/register/"+ip;//节点路径

        byte[] data = "disable".getBytes(charset);//节点值

        CuratorWatcher watcher = new ZKWatchRegister(registerNode,data);    //创建一个register watcher

        Stat stat = zkTools.checkExists().forPath(registerNode);
        if(stat != null){
            zkTools.delete().forPath(registerNode);
        }
        zkTools.create()
                .creatingParentsIfNeeded()          .withMode(CreateMode.EPHEMERAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath(registerNode,data);//创建的路径和值

        //添加到session过期监控事件中
        addReconnectionWatcher(registerNode, ZookeeperWatcherType.CREATE_ON_NO_EXITS,watcher);
        data = zkTools.getData().usingWatcher(watcher).forPath(registerNode);
        System.out.println("get path form zk : "+ registerNode +":"+new String(data,charset));
    }

    public enum ZookeeperWatcherType{
        GET_DATA,GET_CHILDREN,EXITS,CREATE_ON_NO_EXITS
    }

    public class ZKWatch implements CuratorWatcher{
        private final String path;

        public ZKWatch(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        @Override
        public void process(WatchedEvent event) throws Exception {
            System.out.println(event.getType());
            if(event.getType() == Watcher.Event.EventType.NodeDataChanged){
                byte[] data = zkTools.
                        getData().
                        usingWatcher(this).forPath(path);
                System.out.println(path+":"+new String(data,Charset.forName("utf-8")));
            }
        }

    }

    public class ZKWatchRegister implements CuratorWatcher{
        private final String path;
        private byte[] value;
        public ZKWatchRegister(String path,byte[] value) {
            this.path = path;
            this.value = value;
        }

        public String getPath() {
            return path;
        }

        @Override
        public void process(WatchedEvent event) throws Exception {
            System.out.println(event.getType());
            if(event.getType() == Watcher.Event.EventType.NodeDataChanged){
                //节点数据改变了，需要记录下来，以便session过期后，能够恢复到先前的数据状态
                byte[] data = zkTools.
                        getData().
                        usingWatcher(this).forPath(path);
                value = data;
                System.out.println(path+":"+new String(data,charset));
            }else if(event.getType() == Watcher.Event.EventType.NodeDeleted){
                //节点被删除了，需要创建新的节点
                System.out.println(path + ":" + path +" has been deleted.");
                Stat stat = zkTools.checkExists().usingWatcher(this).forPath(path);
                if(stat == null){
                    zkTools.create()
                            .creatingParentsIfNeeded()
                            .withMode(CreateMode.EPHEMERAL)
                            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                            .forPath(path);
                }
            }else if(event.getType() == Watcher.Event.EventType.NodeCreated){
                //节点被创建时，需要添加监听事件（创建可能是由于session过期后，curator的状态监听部分触发的）
                System.out.println(path + ":" +" has been created!" + "the current data is " + new String(value));
                zkTools.setData().forPath(path, value);
                zkTools.getData().usingWatcher(this).forPath(path);
            }
        }
    }
}
