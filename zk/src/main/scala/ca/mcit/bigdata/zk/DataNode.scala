package ca.mcit.bigdata.zk

import org.apache.zookeeper.{CreateMode, ZooDefs}

class DataNode(val groupName: String, val id: String) extends ZKClient {
  def operation(): Unit = {
    val path = s"/summer2019/iraj/$groupName/$id"
    zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL)
  }
}
