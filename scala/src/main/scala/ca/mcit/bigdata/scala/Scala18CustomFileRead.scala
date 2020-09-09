package ca.mcit.bigdata.scala

import scala.io.{BufferedSource, Source}

object Scala18CustomFileRead extends App {

  /** Reads a file (CSV) and applies transformer */
  def customFileRead(fileName: String, transformer: String => String): List[String] = {
    val source: BufferedSource = Source.fromFile(fileName)
    val lines = source.getLines().toList.tail.map(transformer)
    source.close()
    lines
  }

  val movieFileName = "/Users/iraj.hedayati/codes/scala-playground/data/movie.csv"
  // Transformer is "identity" function which doesn't do anything
  customFileRead(movieFileName, identity).foreach(println)

  println("Second transformer")
  // Transformer converts each input to a Movie
  customFileRead(movieFileName, convertLineToMovie).foreach(println)

  def convertLineToMovie(line: String): String = {
    val fields: Array[String] = line.split(",", -1)
    Movie(fields(0).toInt, fields(1), fields(2).toInt, fields(3)).toString
  }

}
