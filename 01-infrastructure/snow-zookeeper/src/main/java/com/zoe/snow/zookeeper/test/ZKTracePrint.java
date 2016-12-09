package com.zoe.snow.zookeeper.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ZKTracePrint
 *
 * @author Dai Wenqing
 * @date 2016/1/15
 */
public class ZKTracePrint implements  Watcher{
    private static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zk;
    private CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZKTracePrint tool = new ZKTracePrint();
        tool.printZKTrance("10.0.2.7:2181");
    }

    public void connection(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);//连接zk
        latch.await();//等到zk连接完成
    }

    public void process(WatchedEvent event) {
        if(event.getState() == Watcher.Event.KeeperState.SyncConnected)//zk连接完成
            latch.countDown();//解锁
    }

    public void close() throws InterruptedException{
        zk.close();
    }

    public void listTrace(String beginPath) throws IOException{
        if(beginPath == null || "".equals(beginPath) || "/".equals(beginPath))
        {
            beginPath = "/";
        }
        listTrace(beginPath, "");

    }

    public void listTrace(String path,String deep) throws IOException{
        deep = deep + "  ";
        System.out.println(path);
        try {
            List<String> children = zk.getChildren(path, false);
            byte[] data = zk.getData(path, false, null);
            if(children.isEmpty()){
                if(data == null)
                    return;
                System.out.println(deep+new String(data));
            }else{
                if(data != null)
                    System.out.println(deep + new String(data));
                for(String child: children){
                    if(!path.equals("/"))
                        listTrace(path+"/"+child,deep + "  ");
                    else
                        listTrace(path+child,deep + "  ");
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printZKTrance(String hosts) throws IOException, InterruptedException{
        ZKTracePrint zk = new ZKTracePrint();
        zk.connection(hosts);
        zk.listTrace(null);
        zk.close();
    }
}
