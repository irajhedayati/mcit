package ca.mcit.collection

import scala.io.{BufferedSource, Source}

object FilePractice extends App {

  // where is the file on your local filesystem
  val filePath = "/home/bd-user/Downloads/person.csv"
  val source: BufferedSource = Source.fromFile(filePath)

  source.getLines()
      .map(line => line.split(","))
      .map(a => Person2(a(0).toInt, a(1), a(2)))
      .foreach(person => println(person))

  source.close()
}
