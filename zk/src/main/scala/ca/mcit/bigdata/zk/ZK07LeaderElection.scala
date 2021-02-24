package ca.mcit.bigdata.zk

import java.util.concurrent.TimeUnit

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener
import org.apache.curator.framework.state.ConnectionState
import org.apache.curator.framework.recipes.leader.LeaderSelector
import org.apache.curator.retry.ExponentialBackoffRetry

/**
 * An example of an election procedure.
 * - Each `Candidate` process will try to join the election
 * - Once elected, it will keep the leadership for a random number of seconds and exits
 * - Departure of the leader will launch a new election process by ZK automatically
 */
class ZK07LeaderElection(val name: String) extends LeaderSelectorListener {

  val retry = new ExponentialBackoffRetry(1000, 10)
  lazy val client: CuratorFramework = CuratorFrameworkFactory.newClient("quickstart.cloudera:2181", retry)
  lazy val leaderSelector = new LeaderSelector(client, "/fall2019/iraj/election", this)
  /**
   * Start the candidate by joining to an election process
   * 1. Connect to ZK
   */
  def start(): Unit = {
    client.start()
    println(s"$name : Connect to ZK")
    if (!client.blockUntilConnected(10, TimeUnit.SECONDS))
      println(s"$name : Unable to connect to quickstart.cloudera:2181")
    println(s"$name : Connection to ZK was successful! Trying to join to election")
    leaderSelector.setId(name)
    leaderSelector.start()
  }

  override def takeLeadership(client: CuratorFramework): Unit = {
    println(s"$name : I'm the leader")
    // wait for a random number of seconds (between 1 to 10) to act as leader and then exit
    // It will trigger new leadership
    val waitTime = (math.random() * 10).toInt
    println(s"$name : stay a leader for $waitTime seconds")
    Thread.sleep(waitTime * 1000)
  }

  override def stateChanged(client: CuratorFramework, newState: ConnectionState): Unit = println(newState)
}

object ZK07LeaderElection extends App {
  println("Launch 5 instance of candidates at the same time")
  println("This is only for demonstration purpose. Most probably, a candidate is an standalone application")
  (1 to 5).foreach(id => new ZK07LeaderElection(s"Candidate #$id").start())
  println("Keep application running; you should kill the application manually")
  while(true) Thread.sleep(1000)
}
