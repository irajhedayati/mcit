package ca.mcit.bigdata.scala

import scala.io.{BufferedSource, Source}

object Scala14Iterator extends App {

  val source: BufferedSource =
    Source.fromFile("/Users/iraj.hedayati/codes/scala-playground/data/movie.csv")

  val lineIterator = source.getLines()

  // A while loop;
  // - condition: continue to execute the code block till the condition evaluates to true
  // - operation code block: business logic

  // The following doesn't print anything as condition evaluates to false always
  // while(false) println("John")
  // The following line prints "Joe" infinitely as the condition evaluates to true always
  // while(true) println("Joe")

  while (lineIterator.hasNext) {
    val line = lineIterator.next()
    println(line)
  }

  source.close()


}
