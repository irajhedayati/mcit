package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.{Movie, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark02CoreHdfs extends App with Base {
  // 0 configuration
  val sparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("Spark Core Practice")
  // 1 spark context
  val sc = new SparkContext(sparkConf)

  val movies = sc.textFile("/user/fall2019/iraj/movie/movie.csv")
  val moviesRdd: RDD[Movie] = movies.filter(!_.contains("mID")).map(Movie.fromCsv)

  val ratingsRdd: RDD[Rating] =
    sc.textFile("/user/fall2019/iraj/rating/rating.csv")
      .filter(!_.contains("mID"))
      .map(Rating(_))

  val x: RDD[(Int, Movie)] = moviesRdd.keyBy(_.mId)
  val y: RDD[(Int, Rating)] = ratingsRdd.keyBy(_.mID)

  val enrichedRating = x.join(y).map {
    case (_, (movie, rating)) => Rating.toCsv(rating, movie)
  }

  enrichedRating.take(20).foreach(println)

  // 3 stop it
  sc.stop()

}
