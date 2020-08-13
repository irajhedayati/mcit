package ca.dataedu.spark.core

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object RatingEnricher extends App {
  // 1. create streaming context
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Spark Streaming/SQL enrichment")
    .master("local[*]")
    .getOrCreate()
  val sc = spark.sparkContext
  val ssc: StreamingContext = new StreamingContext(sc, Seconds(5))


  // 2. source
  val kafkaConfig = Map[String, String] (
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.GROUP_ID_CONFIG -> "rating-enricher-1",
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest",
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> "false"
  )
  val topic = "rating"
  val messages: InputDStream[ConsumerRecord[String, String]] =
    KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](List(topic), kafkaConfig)
    )
  val lines: DStream[String] = messages.map(_.value())

  // 3. business logic
  case class Movie(mID: Int, title: String, year: Int, director: String)
  val movies: RDD[Movie] = spark.sparkContext
    .textFile("/user/fall2019/iraj/movie/movie.csv")
    .filter(!_.contains("mID"))
    .map(_.split(",", -1))
    .map(row => Movie(row(0).toInt, row(1), row(2).toInt, row(3)))
  val keyByIdMovies = movies.keyBy(_.mID)

  case class Rating(rID: Int, mID: Int, stars: Int, ratingDate: Option[String])
  object Rating {
    /**
      * 201,101,2,2011-01-22
      * 202,106,4,
      */
    def apply(csv: String): Rating = {
      val fields = csv.split(",", -1)
      val ratingDate = if (fields(3).isEmpty) None else Option(fields(3))
      Rating(fields(0).toInt, fields(1).toInt, fields(2).toInt, ratingDate)
    }
    def toCsv(rating: Rating, movie: Movie): String =
      s"${rating.rID},${rating.stars},${movie.title}"
  }
  lines.foreachRDD( rdd => { // Business logic per micro-batch
    val rating: RDD[Rating] = rdd.map(Rating(_))
    val keyByMovieRating: RDD[(Int, Rating)] = rating.keyBy(_.mID)
    val x: RDD[(Int, (Rating, Movie))] = keyByMovieRating.join(keyByIdMovies)
    val enrichedRating: RDD[String] = x.map {
      case (_, (rating, movie)) => Rating.toCsv(rating, movie)
    }
    enrichedRating.saveAsTextFile("/user/fall2019/iraj/enriched_rating/")
  })

  // 4. cleanup
  ssc.start()
  ssc.awaitTermination()
}
