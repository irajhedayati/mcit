package ca.dataedu.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkCoreHdfs extends App {
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
      .map(Rating.fromCsv)

  val x: RDD[(Int, Movie)] = moviesRdd.keyBy(_.mId)
  val y: RDD[(Int, Rating)] = ratingsRdd.keyBy(_.mID)

  val enrichedRating = x.join(y).map {
    case (mId, (movie, rating)) => Rating.toCsv(rating, movie)
  }

  enrichedRating.take(20).foreach(println)

  // 3
  sc.stop()

}

case class Movie(mId: Int, title: String, year: Int, director: Option[String])
object Movie {
  def fromCsv(movie: String): Movie = {
    val fields = movie.split(",", -1)
    Movie(fields(0).toInt, fields(1), fields(2).toInt, if (fields.length == 4) Option(fields(3)) else None)
  }
}
case class Rating(rID: Int, mID: Int, stars: Int, ratingDate: Option[String])
object Rating {
  def fromCsv(csv: String): Rating = {
    val fields = csv.split(",", -1)
    val ratingDate = if (fields(3).isEmpty) None else Option(fields(3))
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toInt, ratingDate)
  }
  def toCsv(rating: Rating, movie: Movie): String =
    s"${rating.rID},${rating.stars},${movie.title}"
}
