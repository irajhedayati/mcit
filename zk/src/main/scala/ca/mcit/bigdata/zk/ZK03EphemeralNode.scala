package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, ZooDefs, ZooKeeper}

object ZK03EphemeralNode extends App {

  // 1. ZK client (it's same as running zkCli.sh)
  // zkCli.sh -server quickstart.cloudera:2181
  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 500, new DummyWatcher)

  // 2. Create an ephemeral node
  zkClient.create("/bdsf2001/iraj", "My ephemeral node".getBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL)

  println("Keep the application up forever....")
  while(true) Thread.sleep(1000)

  // n. close the connection
  zkClient.close()

}
