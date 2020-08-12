package ca.dataedu.spark.core

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSqlJoin extends App {

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

  spark.sql(
    """SELECT movie.title, avg(rating.stars) AS rating
      |FROM movie JOIN rating ON movie.mID = rating.mID
      |GROUP BY movie.title
      |""".stripMargin)
    .show()

  movieDf
    .join(ratingDf, "mID")
    .groupBy("mID")
    .agg(avg("stars").as("rating"))
    .show()

  spark.stop()

}
