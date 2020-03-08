package ca.mcit.bigdata

import scala.io.Source


object HelloWorld extends App {
   //println("Hello world!")
  val fileSource = Source.fromFile("/home/bd-user/Documents/person.csv")

  fileSource
    .getLines()                    // a collection of lines

    .map(_.split(","))  // a collection of collection of fields
    .map(p => Person(p(0), p(1).toInt)) // a collection of Persons

    .foreach(println)

  fileSource.close()
}

/*
object HelloWorld {

   def main(args: Array[String]): Unit = {
      println("Hello world!")
   }

}
*/
