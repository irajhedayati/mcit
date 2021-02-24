package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, ZooDefs, ZooKeeper}

object ZK04CreateGroup extends App {

  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 500, new DummyWatcher)
  zkClient.create("/bdsf2001/iraj/zoo", "Iraj's Group".getBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
  zkClient.close()

}
