package ca.mcit.bigdata.hadoop

import org.apache.hadoop.fs.Path

object Hadoop02CopyFromLocal extends HadoopClient with App {

  // hadoop fs -copyFromLocal /local/file /remote/path
  fs.copyFromLocalFile(new Path("data/movie.csv"), new Path("/user/iraj"))

}
