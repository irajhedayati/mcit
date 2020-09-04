package ca.mcit.bigdata.scala

import scala.io.{BufferedSource, Source}

object Scala8Etc extends App {

  // code block
  {
    val name = "John"
    println(s"hello $name!")
  }

  /*
  A code block can return a value
  The last line of the block is the return value; using "return" is not necessary
   */
  val x: String = {
    val name = "Joe"
    s"hello $name!"
  }
  println(x)


  /** Break a statement or expression */
  val source: BufferedSource =
    Source
      .fromFile("/Users/iraj.hedayati/codes/scala-playground/data/movie.csv")

}
