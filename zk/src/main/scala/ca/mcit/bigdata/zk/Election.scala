package ca.mcit.bigdata.zk

import java.util.concurrent.TimeUnit

import org.apache.curator.framework.recipes.leader.{LeaderSelector, LeaderSelectorListener}
import org.apache.curator.framework.state.ConnectionState
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry

class CustomListener extends LeaderSelectorListener {
  override def takeLeadership(client: CuratorFramework): Unit = {
    println("I'm the leader")
    while(Election.selector.hasLeadership) {
      print("Still the leader")
      Thread.sleep(1000)
    }
  }

  override def stateChanged(client: CuratorFramework, newState: ConnectionState): Unit =
    println(newState)
}

object Election extends App {

  val r = new ExponentialBackoffRetry(1000, 10)
  val client = CuratorFrameworkFactory.newClient("172.16.129.58:2181", r)

  client.start()
  if (!client.blockUntilConnected(10, TimeUnit.SECONDS))
    println("Unable to connect!!!")
  val selector = new LeaderSelector(client, "/summer2019/thakkar/election", new CustomListener)
  selector.setId("leader1")
  selector.autoRequeue()
  selector.start()

  while(true) Thread.sleep(1000)

}
