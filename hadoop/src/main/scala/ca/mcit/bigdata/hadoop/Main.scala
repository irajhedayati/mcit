package ca.mcit.bigdata.hadoop

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

trait Main {
  val conf = new Configuration()
  conf.addResource(new Path("/home/bd-user/opt/hadoop/etc/cloudera/core-site.xml"))
  conf.addResource(new Path("/home/bd-user/opt/hadoop/etc/cloudera/hdfs-site.xml"))
  val fs = FileSystem.get(conf)
}
