package ca.mcit.bigdata.hadoop

import org.apache.hadoop.fs.Path

object Hadoop03Directories extends HadoopClient with App {

  /*
  hadoop fs -ls /
  */
  fs
    .listStatus(new Path("/"))
    .map(_.getPath)
    .foreach(println)

  /*
  hadoop fs -mkdir /user/winter2020/iraj/new_folder3

  You need to impersonate the user using HADOOP_USER_NAME environment variable.
   */
  fs.mkdirs(new Path("/user/winter2020/iraj/new_folder3"))

  /*
  hadoop fs -rm -R /user/winter2020/iraj/new_folder2
   */
  fs.delete(new Path("/user/winter2020/iraj/new_folder2"), true)

}
