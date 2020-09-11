package ca.mcit.bigdata.scala

import ca.mcit.bigdata.scala.schema.{EnrichedRating, Movie, Rating}

import scala.io.Source

/** When there is a massive amount of data coming from I/O which is blocking OR it may explode */
object Scala21IteratorUseCase extends App {

  val movie = Source.fromFile("/Users/iraj.hedayati/codes/scala-playground/data/movie.csv")
  val rating = Source.fromFile("/Users/iraj.hedayati/codes/scala-playground/data/rating.csv")

  /** It's blocking and it would keep data in memory which could explode; we don't want to do that */
  // val lines = source.getLines().toList

  // Data enrichment
  // data that I can keep in the memory which is small
  val movies: List[Movie] = movie.getLines().toList.map(convertLineToMovie)
  val lookupTable: Map[Int, Movie] = movies.map(route => route.mID -> route).toMap

  val sourceItr = rating.getLines()
  while(sourceItr.hasNext) {
    val nextRating = sourceItr.next()
    val fields = nextRating.split(",", -1)
    val rating = Rating(fields(0).toInt, fields(1).toInt, fields(2).toInt)
    // Apply business logic
    val matchingMovie = lookupTable.get(rating.mID).orNull
    val enrichedRating = EnrichedRating(rating, matchingMovie)
    println(enrichedRating)
  }

  rating.close()
  movie.close()

  def convertLineToMovie(line: String): Movie = {
    val fields: Array[String] = line.split(",", -1)
    Movie(fields(0).toInt, fields(1), fields(2).toInt, fields(3))
  }
}
