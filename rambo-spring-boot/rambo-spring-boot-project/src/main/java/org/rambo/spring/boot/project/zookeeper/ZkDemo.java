/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.zookeeper -> sad.java
 * Created on 6 Oct 2017-4:55:48 pm
 */
package org.rambo.spring.boot.project.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  6 Oct 2017 4:55:48 pm
 */

public class ZkDemo {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 创建一个与服务器的连接
        ZooKeeper zk = null;


        zk = new ZooKeeper("172.21.76.189:2181", 60000, new Watcher() { 
        	// 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("EVENT:" + event.getType());
            }});


        // 查看根节点
        System.out.println("ls / => " + zk.getChildren("/", true));

        // 创建一个目录节点
        if (zk.exists("/node", true) == null) {
            zk.create("/node", "yb".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("create /node yb");
            // 查看/node节点数据
            System.out.println("get /node => " + new String(zk.getData("/node", false, null)));
            // 查看根节点
            System.out.println("ls / => " + zk.getChildren("/", true));
        }

        // 创建一个子目录节点
        if (zk.exists("/node/sub1", true) == null) {
            zk.create("/node/sub1", "sub1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("create /node/sub1 sub1");
            // 查看node节点
            System.out.println("ls /node => " + zk.getChildren("/node", true));
        }

        // 修改节点数据
        if (zk.exists("/node", true) != null) {
            zk.setData("/node", "changed".getBytes(), -1);
            // 查看/node节点数据
            System.out.println("get /node => " + new String(zk.getData("/node", false, null)));
        }

        // 删除节点
        if (zk.exists("/node/sub1", true) != null) {
            zk.delete("/node/sub1", -1);
            zk.delete("/node", -1);
            // 查看根节点
            System.out.println("ls / => " + zk.getChildren("/", true));
        }

        // 关闭连接
        zk.close();
    }
}
