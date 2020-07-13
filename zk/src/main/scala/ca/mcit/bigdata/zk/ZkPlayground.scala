package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, WatchedEvent, Watcher, ZooDefs, ZooKeeper}

object ZkPlayground extends App {

  val zkClient = new ZooKeeper("quickstart.cloudera:2181", 3000, new DummyWatcher)

  zkClient.create("/zoo", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)

  zkClient.close()

}

class DummyWatcher extends Watcher {
  override def process(event: WatchedEvent): Unit = () // Noop
}
