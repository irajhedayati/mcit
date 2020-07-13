package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, ZooDefs, ZooKeeper}

object ZkGmP1 extends App {

  val zkClient = new ZooKeeper("zk1:2181,zk2:2181", 3000, new DummyWatcher)

  zkClient.exists("/zoo", false)
  zkClient.create(
    "/zoo/p1",
    "http://p1.quickstart.cloudera:2181".getBytes,
    ZooDefs.Ids.OPEN_ACL_UNSAFE,
    CreateMode.EPHEMERAL
  )

  while(true) Thread.sleep(1000)

  zkClient.close()

}
