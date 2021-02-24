package ca.mcit.bigdata.zk

import org.apache.zookeeper.ZooKeeper

object ZK02DeleteNode extends App {

  // 1. ZK client (it's same as running zkCli.sh)
  // zkCli.sh -server quickstart.cloudera:2181
  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 500, new DummyWatcher)

  // 2. Create an persistent node
  zkClient.delete("/bdsf2001/iraj", 0)

  // n. close the connection
  zkClient.close()

}
