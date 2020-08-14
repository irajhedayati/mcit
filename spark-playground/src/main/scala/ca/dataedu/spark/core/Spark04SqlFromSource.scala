package ca.dataedu.spark.core

import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark04SqlFromSource extends App with Base {

  /** on HDFS */
  val fileName = "/user/fall2019/iraj/movie/movie.csv"

  val spark = SparkSession.builder().appName("Spark SQL practice").master("local[*]").getOrCreate()

  val movieDf: DataFrame = spark.read.option("header", "true").option("inferschema", "true").csv(fileName)

  spark.stop()
}
