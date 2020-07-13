package ca.mcit.bigdata.hadoop

import ca.mcit.bigdata.hadoop.HadoopPlayground.fs
import org.apache.hadoop.fs.Path

object HadoopLs extends HdfsClient {
  /*
    hadoop fs -ls /
    */
  fs
    .listStatus(new Path("/"))
    .map(_.getPath)
    .foreach(println)
}
