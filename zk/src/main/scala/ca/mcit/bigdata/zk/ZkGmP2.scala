package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, ZooDefs, ZooKeeper}

object ZkGmP2 extends App {

  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 3000, new DummyWatcher)

  zkClient.create(
    "/zoo/p2",
    "http://p2.quickstart.cloudera:2181".getBytes,
    ZooDefs.Ids.OPEN_ACL_UNSAFE,
    CreateMode.EPHEMERAL
  )

  while(true) Thread.sleep(1000)

  zkClient.close()

}
