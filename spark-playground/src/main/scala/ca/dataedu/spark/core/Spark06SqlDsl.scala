package ca.dataedu.spark.core

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark06SqlDsl extends App with Base {

  /** on HDFS */
  val fileName = "/user/fall2019/iraj/movie/movie.csv"

  val spark = SparkSession.builder()
    .appName("Spark SQL practice").master("local[*]")
    .getOrCreate()

  import spark.implicits._

  val movieDf: DataFrame = spark.read.option("header", "true").option("inferschema", "true").csv(fileName)

  val movieAfter2000: DataFrame = movieDf
    .select($"mID", $"title")
    .filter($"year" > 2000)

  val movieAfter2000Col: DataFrame = movieDf
    .select(col("mID"), col("title"))
    .filter(col("year") > 2000)

  movieAfter2000.show()

  spark.stop()
}
