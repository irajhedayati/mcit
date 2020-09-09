package ca.mcit.bigdata.scala

import scala.io.{BufferedSource, Source}

object Scala12ReadData extends App {

  val source: BufferedSource =
    Source.fromFile("/Users/iraj.hedayati/codes/scala-playground/data/movie.csv")

  val lines: List[String] = source.getLines().toList

  // just print out the lines; so we used foreach
  lines.foreach(printLine)

  println("===== convert input to our internal data model for any data processing purpose")
  val movies: List[Movie] = lines.tail.map(convertLineToMovie)
  movies.foreach(println)

  println("===== Movies producer after 2000")
  val recentMovies: List[Movie] = movies.filter(recentMovieFilter)
  recentMovies.foreach(println)

  println("===== Movies producer before 2000")
  val oldMovies: List[Movie] = movies.filterNot(recentMovieFilter)
  oldMovies.foreach(println)

  println("===== Apply multiple conditions")
  val oldMoviesOfCameron = movies.filter(oldMovieDirectorFilter)
  oldMoviesOfCameron.foreach(println)

  source.close()

  /**
    * Accepts a line as string and print it out.
    * This function returns nothing and the type is Unit
    */
  def printLine(line: String): Unit = println(line)

  /** Convert a CSV line to an instance of Movie
    * 1. split the line */
  def convertLineToMovie(line: String): Movie = {
    val fields: Array[String] = line.split(",", -1)
    Movie(fields(0).toInt, fields(1), fields(2).toInt, fields(3))
  }

  def recentMovieFilter(movie: Movie): Boolean = movie.year >= 2000

  def oldMovieDirectorFilter(movie: Movie): Boolean = movie.year < 2000 && movie.director == "James Cameron"

}
