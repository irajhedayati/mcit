package ca.dataedu.spark.core

import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.SparkSession

object Spark17MLCorrelation extends App with Base {

  /*
  Correlation is a measure that indicates how two variables affect each other
   */

  val spark = SparkSession.builder().appName("Spark ML").master("local[*]").getOrCreate()

  val raining = spark.read.option("header", "true").option("inferschema", "true").csv("/user/iraj/raining.csv")
  raining.describe().show()

  // feature RDD
  val rainfallRdd = raining.rdd.map(_.getDouble(1))
  // label RDD
  val umbrellasSold = raining.rdd.map(_.getInt(2).toDouble)

  println(Statistics.corr(rainfallRdd, umbrellasSold))

  spark.stop()

}
