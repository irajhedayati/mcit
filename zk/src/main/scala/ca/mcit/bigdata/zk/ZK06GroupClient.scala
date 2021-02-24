package ca.mcit.bigdata.zk

import org.apache.zookeeper.ZooKeeper

import scala.collection.JavaConverters._

object ZK06GroupClient extends App {

  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 500, new DummyWatcher)

  val processList = zkClient.getChildren("/bdsf2001/iraj/zoo", false).asScala.toList
  processList.foreach(println)

  zkClient.close()

}
