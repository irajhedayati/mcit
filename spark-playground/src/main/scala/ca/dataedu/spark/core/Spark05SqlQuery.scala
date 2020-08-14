package ca.dataedu.spark.core

import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark05SqlQuery extends App with Base {

  /** on HDFS */
  val fileName = "/user/fall2019/iraj/movie/movie.csv"

  val spark = SparkSession.builder()
    .appName("Spark SQL practice").master("local[*]")
    .getOrCreate()

  val movieDf: DataFrame = spark.read.option("header", "true").option("inferschema", "true").csv(fileName)

  movieDf.createOrReplaceTempView("movie")

  val movieAfter2000: DataFrame = spark.sql(
    """SELECT mID, title
      |FROM movie
      |WHERE year > 2000""".stripMargin
  )

  movieAfter2000.show()

  spark.stop()

}
