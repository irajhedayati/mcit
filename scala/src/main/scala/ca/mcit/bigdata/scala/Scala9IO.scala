package ca.mcit.bigdata.scala

import scala.io.{BufferedSource, Source}

/**
  * Reading file is done through Source object. There are 3 steps:
  * - open the source
  * - get the lines
  * - close the source to free up resources
  */
object Scala9IO extends App {

  // Read a local file
  val source: BufferedSource =
    Source.fromFile("/Users/iraj.hedayati/codes/scala-playground/data/movie.csv")

  val lines = source.getLines()

  source.close()

  // Read file from a URL
  val onlineSource: BufferedSource =
    Source.fromURL("https://raw.githubusercontent.com/irajhedayati/mcit/master/hive/movie.csv")

  val onlineLines = source.getLines()

  onlineSource.close()

}
