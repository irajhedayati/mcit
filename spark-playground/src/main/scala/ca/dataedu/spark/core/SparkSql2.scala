package ca.dataedu.spark.core

import ca.dataedu.spark.core.SparkSql.filteredDf
import org.apache.spark.sql.SparkSession

object SparkSql2 extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Spark SQL practices")
    .master("local[*]")
    .getOrCreate()

  val tripDf = spark.read
      .option("header", "true")
      .option("inferschema", "true")
      .csv("/user/iraj/trips.txt")

//  tripDf.printSchema()
//  tripDf.show(10, false)

  tripDf.createOrReplaceTempView("iraj_trip")
  val filteredDf = spark.sql(
    """SELECT trip_headsign
      |FROM iraj_trip
      |WHERE route_id=2
      |""".stripMargin)
  filteredDf.show(30, truncate = false)

  spark.close()

}
