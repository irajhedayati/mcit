package ca.mcit.bigdata.hadoop

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}

object Hadoop01Init extends App {

  // 1. Set the configuration
  val conf = new Configuration()
  val hadoopConfDir = "/Users/iraj.hedayati/opt/hadoop-2.7.3/etc/cloudera"
  conf.addResource(new Path(s"$hadoopConfDir/core-site.xml"))
  conf.addResource(new Path(s"$hadoopConfDir/hdfs-site.xml"))

  // 2. Create the client
  val fs = FileSystem.get(conf)

  // 3. Send commands to HDFS: hadoop fs -ls /
  val content: Array[FileStatus] = fs.listStatus(new Path("/"))
  content.map(_.getPath).foreach(println)

  fs.mkdirs(new Path("/user/iraj/test"))

  // N. close filesystem to free resources
  fs.close()

}
