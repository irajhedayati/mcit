package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.Movie
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Spark03Sql extends App with Base {

  val spark = SparkSession.builder()
    .appName("Spark SQL practice").master("local[*]")
    .getOrCreate()
  import spark.implicits._

  val movieRdd: RDD[String] = spark.sparkContext.parallelize(List(
    "101,Gone with the Wind,1939,Victor Fleming",
    "102,Star Wars,1977,George Lucas"
  ))
  // Inferring schema using reflection
  val movieRdd2: RDD[Movie] = movieRdd
    .map(_.split(","))
    .map(csv => new Movie(csv(0).toInt, csv(1), csv(2).toInt, Some(csv(3))))
  val movieDf: DataFrame = movieRdd2.toDF

  movieDf.show()
  movieDf.printSchema()

  // programmatic interface
  val movieRdd3: RDD[Row] = movieRdd
    .map(_.split(","))
    .map(csv => Row(csv(0).toInt, csv(1), csv(2).toInt, csv(3)))
  val schema: StructType = StructType(List(
    StructField("mID", IntegerType, nullable = false),
    StructField("title", StringType, nullable = false),
    StructField("year", IntegerType),
    StructField("director", StringType)
  ))
  val movieDf2: DataFrame = spark.createDataFrame(movieRdd3, schema)

  movieDf2.show()
  movieDf2.printSchema()

  spark.stop()

}
