package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.Movie
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Spark03Sql extends App with Base {

  // 0. create spark session (it contains a spark context)
  val spark = SparkSession.builder().appName("Spark SQL Practices").master("local[*]").getOrCreate()

  // 1. how to get the spark context; to create RDD of String
  val rawMovie: RDD[String] = spark.sparkContext.parallelize(
    List(
      "101,Gone with the Wind,1939,Victor Fleming",
      "102,Star Wars,1977,George Lucas"
    )
  )

  // 2. how ot create DF from RDD using inference
  // 2.a create RDD of "case class" (e.g. RDD[Movie])
  // 2.b use `.toDF` from Spark session implicits
  val movieRdd: RDD[Movie] = rawMovie.map(Movie(_))
  import spark.implicits._
  val movieDf: DataFrame = movieRdd.toDF

  // THIS IS FOR DEBUG AND SHALL NOT BE IN ANY PRODUCTION CODE NOR IN THE SUBMISSION
  movieDf.printSchema()
  movieDf.show() // shows ... records and if the values are big they will be truncated
  movieDf.show(20) // shows 20 records and if the values are big they will be truncated
  movieDf.show(20, truncate = false) // shows 20 records and does not truncate

  // 3. how to create DF from RDD using programming interface
  // 3.a convert it to RDD[Row]
  // 3.b create a schema
  // 3.c apply schema on the RDD
  val movieRows: RDD[Row] = rawMovie
    .map(_.split(",", -1))
    .map(fields => Row(fields(0).toInt, fields(1), fields(2).toInt, fields(3)))
  val schema = StructType(
    List(
      StructField("mId", IntegerType, nullable  = false),
      StructField("title", StringType, nullable = false),
      StructField("year", IntegerType, nullable = true),
      StructField("director", StringType)
    )
  )
  val movieDf2: DataFrame = spark.createDataFrame(movieRows, schema)

  // THIS IS FOR DEBUG AND SHALL NOT BE IN ANY PRODUCTION CODE NOR IN THE SUBMISSION
  movieDf2.printSchema()
  movieDf2.show() // shows ... records and if the values are big they will be truncated

  // 100. stop spark session (which also stoops spark context)
  spark.stop()

}
