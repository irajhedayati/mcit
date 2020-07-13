package ca.mcit.bigdata.hadoop

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

trait HdfsClient extends App {

  private val conf = new Configuration() // loads with default values
  val hdoopConfDir = System.getenv("HADOOP_CONF_DIR")
  conf.addResource(new Path(s"$hdoopConfDir/core-site.xml"))
  conf.addResource(new Path(s"$hdoopConfDir/hdfs-site.xml"))

  val fs: FileSystem = FileSystem.get(conf)

}
