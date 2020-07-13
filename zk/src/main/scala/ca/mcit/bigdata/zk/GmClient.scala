package ca.mcit.bigdata.zk

import java.util

import org.apache.zookeeper.ZooKeeper

object GmClient extends App {

  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 3000, new DummyWatcher)

  val processList: util.List[String] = zkClient.getChildren("/zoo", false)

  processList.forEach(println)

  zkClient.close()

}
