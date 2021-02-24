package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, ZooDefs, ZooKeeper}

object ZK05GroupMember02 extends App {

  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 500, new DummyWatcher)
  zkClient.create(
    "/bdsf2001/iraj/zoo/p2",
    "http://p1.quickstart.cloudera:2181".getBytes,
    ZooDefs.Ids.OPEN_ACL_UNSAFE,
    CreateMode.EPHEMERAL
  )

  println("Keep the application up forever....")
  while (true) Thread.sleep(1000)

  zkClient.close()

}
