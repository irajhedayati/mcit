package ca.mcit.bigdata.hadoop

import org.apache.hadoop.fs.{FSDataOutputStream, Path}

object Hadoop04Writer extends HadoopClient with App {

  /** The path on the HDFS to write data */
  val filePath = new Path("/user/iraj/names.txt")

  /** The list of students to write on HDFS */
  val tutorialList = List("Hadoop01Init", "Hadoop02CopyFromLocal", "Hadoop03Directories", "Hadoop04Writer")

  /** Open a stream from client to the file on HDFS */
  val writer: FSDataOutputStream = fs.create(filePath)

  /** Write names on HDFS */
  tutorialList
    .map(tutorial => s"$tutorial\n")
    .foreach(tutorial => writer.writeChars(tutorial))

  /** clean-up */
  writer.flush()
  writer.close()

}
