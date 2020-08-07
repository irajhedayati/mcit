package ca.dataedu.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSql extends App {

  val sparkSession: SparkSession = SparkSession
    .builder()
    .appName("Spark SQL practices")
    .master("local[*]")
    .getOrCreate()

  // 1. Load the data from HDFS
  // In order to connect Spark to HDFS, we need to set HADOOP_CONF_DIR
  // Using RDD and converting to DF
  val tripsRdd: RDD[Trip] = sparkSession.sparkContext
    .textFile("/user/iraj/trips.txt")
    .filter(!_.contains("route_id"))
    .map(_.split(",", -1))
    .map(row => Trip(row(0), row(1), row(2), row(3)))


//  println(s"${tripsRdd.count()}")
//  println(s"${tripsRdd.getNumPartitions}")

  import sparkSession.implicits._
  val tripsDf: DataFrame = tripsRdd.toDF

//  tripsDf.printSchema()
//  tripsDf.show(10, truncate = false)

//  import org.apache.spark.sql.functions._
//  tripsDf.filter(col("routeId"))
  val filteredDf = tripsDf
  .select($"tripHeadSign")
  .filter($"routeId" === 2)
  .filter($"wheelchair_accessible" === 1)
  filteredDf.show(30, truncate = false)
//  println(filteredDf.count())

  // Close the session
  sparkSession.stop()
}

case class Trip(routeId: String,
                serviceId: String,
                tripId: String,
                tripHeadSign: String)
