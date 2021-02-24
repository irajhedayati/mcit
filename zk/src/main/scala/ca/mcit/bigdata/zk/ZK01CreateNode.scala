package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, ZooDefs, ZooKeeper}

object ZK01CreateNode extends App {

  // 1. ZK client (it's same as running zkCli.sh)
  // zkCli.sh -server quickstart.cloudera:2181
  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 500, new DummyWatcher)

  // 2. Create an persistent node
  zkClient.create("/bdsf2001/iraj", "BigData Fall 2020".getBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)

  // n. close the connection
  zkClient.close()

}
