package ca.mcit.bigdata.zk

import org.apache.zookeeper._

class CreateGroup(val groupName: String) extends ZKClient {

  def operation(): Unit = {
    val path = s"/fall2019/iraj/$groupName"
    zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
  }

}
