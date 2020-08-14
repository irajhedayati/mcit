package ca.dataedu.spark.core

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Spark08SqlWrite extends App with Base {

  /** on HDFS */
  val movieFileName = "/user/fall2019/iraj/movie/movie.csv"
  val ratingFileName = "/user/fall2019/iraj/rating/rating.csv"

  val spark = SparkSession.builder()
    .appName("Spark SQL practice").master("local[*]")
    .getOrCreate()

  val movieDf: DataFrame = spark.read.option("header", "true").option("inferschema", "true").csv(movieFileName)
  val ratingDf: DataFrame = spark.read.option("header", "true").option("inferschema", "true").csv(ratingFileName)

  /** Join dataframes using SQL query */
  movieDf.createOrReplaceTempView("movie")
  ratingDf.createOrReplaceTempView("rating")

  val movieRatings = spark.sql(
    """SELECT movie.title, avg(rating.stars) AS rating
      |FROM movie JOIN rating ON movie.mID = rating.mID
      |GROUP BY movie.title
      |""".stripMargin)

  movieRatings.coalesce(1).write.mode(SaveMode.Overwrite).json("/user/fall2019/iraj/movie_rating_json")

  spark.stop()

}
