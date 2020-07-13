package ca.mcit.bigdata.hadoop

import org.apache.hadoop.fs.{FSDataOutputStream, Path}

object HadoopPlayground extends HdfsClient {


  /**
    * The "copyFromLocal" command has 4 different APIs in the FileSystem.
    * It uses overloading in Java which is "same function name, different parameters"
    */

  // hadoop fs -copyFromLocal /local/file /remote/path
  fs.copyFromLocalFile(false, new Path("/local/file1"), new Path("/remote/path"))
  fs.copyFromLocalFile(false, new Path("/local/file2"), new Path("/remote/path"))
  fs.copyFromLocalFile(false, new Path("/local/file3"), new Path("/remote/path"))
  fs.copyFromLocalFile(false, new Path("/local/file4"), new Path("/remote/path"))
  fs.copyFromLocalFile(false, new Path("/local/fil5"), new Path("/remote/path"))
  /**
    * hadoop fs -ls /remote/path
    * - file1
    * - file2
    *
    */
  fs.copyFromLocalFile(
    false,
    false,
    Array(new Path("/local/file1"), new Path("/local/file2")),
    new Path("/remote/path")
  )

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

  /** The path on the HDFS to write data */
  val filePath = new Path("/user/winter2020/iraj/new_folder3/students")

  /** The list of students to write on HDFS */
  val names = List("Ramandeep", "Jemish", "Vamsi", "Parvatheesam", "Vasu", "Jigar")

  /** Open a stream from client to the file on HDFS */
  val y: FSDataOutputStream = fs.create(filePath)

  /** Write names on HDFS */
  names.foreach(name => y.writeChars(name))

  /** clean-up */
  y.flush()
  y.close()
}
