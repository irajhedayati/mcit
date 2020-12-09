package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.Rating
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Spark11StreamingWithSql extends App with Base {

  // 0. Spark conf
  val spark = SparkSession.builder()
    .appName("Spark SQL practice").master("local[*]")
    .getOrCreate()
  val ssc = new StreamingContext(spark.sparkContext, Seconds(10))

  // 1. read dimension into dataframe
  /** on HDFS */
  val fileName = "/user/fall2019/iraj/movie/movie.csv"
  val movieDf: DataFrame = spark.read.option("header", "true").option("inferschema", "true").csv(fileName)

  // 2. Create the stream (DStream)
  val fileStream: DStream[String] = ssc.textFileStream("/user/fall2019/iraj/stream_input/")

  // 3. Business logic
  // networkSocketStream.print()
  fileStream.foreachRDD{ rdd =>
    import spark.implicits._
    val ratingDf = rdd.map(Rating(_)).toDF()
    movieDf.createOrReplaceTempView("movie")
    ratingDf.createOrReplaceTempView("rating")
    val joinResult = spark.sql(
      """SELECT movie.title, avg(rating.stars) AS rating
        |FROM movie JOIN rating ON movie.mID = rating.mID
        |GROUP BY movie.title
        |""".stripMargin)
    joinResult.coalesce(1).write.mode(SaveMode.Append).json("/user/fall2019/iraj/movie_rating_json2")

  }

  // 4. Start the streaming and keep it running
  ssc.start()
  ssc.awaitTermination()
  ssc.stop(stopSparkContext = true, stopGracefully = true)

}
