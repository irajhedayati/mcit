package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.Rating
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Spark12StreamingWithSql extends App with Base {

  /**
    * Problem:
    * - I have all the movies on HDFS.
    * - I get rating information regularly through FTP on the HDFS in CSV format
    * - Once I received the rating file(s), I'd like to calculate the average rating
    *
    * Limitation: the average is calculated per batch and it is not a global average
    */
  val spark = SparkSession.builder().appName("Spark SQL Practices").master("local[*]").getOrCreate()
  val ssc = new StreamingContext(spark.sparkContext, Seconds(10))

  val movieDf = spark.read
    .option("header", "true")
    .option("inferschema", "true")
    .csv("/user/bdsf2001/iraj/movie/movie.csv")

  val ratingStream = ssc.textFileStream("/user/bdsf2001/iraj/rating/")

  ratingStream.foreachRDD { ratingRdd =>
    import spark.implicits._
    val ratingDf = ratingRdd.filter(!_.contains("rID")).map(Rating(_)).toDF()
    movieDf.createOrReplaceTempView("movie")
    ratingDf.createOrReplaceTempView("rating")
    val enrichedRating1 = spark.sql(
      """SELECT movie.title, avg(rating.stars) AS rating
        |FROM movie JOIN rating ON movie.mID = rating.mID
        |GROUP BY movie.title
        |""".stripMargin
    )
    enrichedRating1.show()
  }

  ssc.start()
  ssc.awaitTermination()
  ssc.stop(stopSparkContext = true, stopGracefully = true)

}
