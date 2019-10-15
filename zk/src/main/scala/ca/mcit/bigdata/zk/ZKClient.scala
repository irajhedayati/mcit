package ca.mcit.bigdata.zk

import java.util.concurrent.CountDownLatch

import org.apache.zookeeper.Watcher.Event
import org.apache.zookeeper.{CreateMode, WatchedEvent, Watcher, ZooDefs, ZooKeeper}

trait ZKClient extends Watcher {

  var zk: ZooKeeper = null
  private val connectedSignal: CountDownLatch = new CountDownLatch(1)

  override def process(event: WatchedEvent): Unit =
    if (event.getState == Event.KeeperState.SyncConnected) connectedSignal.countDown()

  def connect(host: String): Unit = {
    zk = new ZooKeeper(host, 5000, this)
    connectedSignal.await()
  }

  def close(): Unit = zk.close()

  def operation(): Unit

}
