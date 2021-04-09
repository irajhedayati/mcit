package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.{Movie, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark02CoreHdfs extends App with Base {
  // 0. Spark Configuration
  val sparkConf = new SparkConf().setAppName("Spark Core Practices").setMaster("local[*]")
  // 1. Spark Context
  val sc = new SparkContext(sparkConf)
  val movies = sc.textFile("/user/bdsf2001/iraj/movie/movie.csv")
  val movieRdd: RDD[Movie] = movies
    .filter(!_.contains("mID")) // filter out the header (there is no other way in Spark core
    .map(Movie(_)) // transformation
  /* ~~~~~~~~~ just debugging; must be removed from the project ~~~~~~~ */
  movieRdd
    .take(10) // Action to return 10 items (our movie file has less than 10 lines)
    .foreach(println)

  val ratingRdd: RDD[Rating] = sc
    .textFile("/user/bdsf2001/iraj/rating/rating.csv")
    .filter(!_.contains("mID"))
    .map(Rating(_))
  /* ~~~~~~ prepare RDDs for join ~~~~~~~~~ */
  val a: RDD[(Int, Movie)] = movieRdd.keyBy(_.mId)
  val b: RDD[(Int, Rating)] = ratingRdd.keyBy(_.mID)

  /* JOIN */
  val enrichedRating = a.join(b).map {
    case (_, (movie, rating)) => Rating.toCsv(rating, movie)
  }
  enrichedRating.take(30).foreach(println)

  enrichedRating.saveAsTextFile("/user/bdsf2001/iraj/enriched_rating/")

  // 100. stop the context
  sc.stop()

}
