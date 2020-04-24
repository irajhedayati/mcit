package ca.mcit.bigdata.zk

object Main extends App {
  val c = new CreateGroup("zoo")
  c.connect("quickstart.cloudera:2181")
  c.operation()
  c.close()
}
