package ca.mcit.bigdata.zk

object JoinGroupTesting extends App {

  val server = "quickstart.cloudera:2181"

  val dn1 = new DataNode("zoo", "dn1")
  dn1.connect(server)
  dn1.operation()

  val dn2 = new DataNode("zoo", "dn2")
  dn2.connect(server)
  dn2.operation()

  val dn3 = new DataNode("zoo", "dn3")
  dn3.connect(server)
  dn3.operation()

  Thread.sleep(Long.MaxValue)

}
