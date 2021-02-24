package ca.mcit.bigdata.hadoop

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

trait HdfsClientFromFile {

  // 1. Set the configuration
  val conf = new Configuration()
  val hadoopConfDir = "/Users/iraj.hedayati/opt/hadoop-2.7.3/etc/cloudera"
  conf.addResource(new Path(s"$hadoopConfDir/core-site.xml"))
  conf.addResource(new Path(s"$hadoopConfDir/hdfs-site.xml"))

  // 2. Create the client
  val fs: FileSystem = FileSystem.get(conf)

}
