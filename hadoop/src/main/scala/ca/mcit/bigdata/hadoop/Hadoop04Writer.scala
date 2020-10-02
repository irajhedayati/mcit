package ca.mcit.bigdata.hadoop

import org.apache.hadoop.fs.{FSDataOutputStream, Path}

object Hadoop04Writer extends HadoopClient with App {

  /** The path on the HDFS to write data */
  val filePath = new Path("/user/iraj/names.txt")

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
