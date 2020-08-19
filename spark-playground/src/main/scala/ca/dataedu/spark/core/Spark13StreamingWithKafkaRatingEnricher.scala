package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.Rating
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Spark13StreamingWithKafkaRatingEnricher extends App with Base {

  // 1. Create Spark streaming context
  val spark = SparkSession.builder()
    .master("local[*]").appName("Spark streaming with Kafka for join")
    .getOrCreate()
  // 1.c A Spark streaming context
  val ssc = new StreamingContext(spark.sparkContext, Seconds(10))

  // 2. Create DStream
  // 2.a Create Kafka consuming configuration
  val kafkaConfig = Map[String, String] (
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.GROUP_ID_CONFIG -> "test",
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest",
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> "false"
  )
  // 2.b Create the stream and subscribe to the topic(s)
  val topic = "rating"
  val inStream: DStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
    ssc,
    LocationStrategies.PreferConsistent,
    ConsumerStrategies.Subscribe[String, String](List(topic), kafkaConfig)
  )

  // 3. Business logic for each micro-batch (a micro-batch is an RDD)
  // 3.a read the dimension into a dataframe
  val movieFileName = "/user/fall2019/iraj/movie/movie.csv"
  val movieDf = spark.read
    .option("header", "true")
    .csv(movieFileName)
  // 3.b for each micro-batch, convert CSV to Rating and join it with movie dimension
  inStream.map(_.value()).foreachRDD(rdd => businessLogic(rdd))

  // 4. Start streaming and keep running
  ssc.start()
  ssc.awaitTermination()

  def businessLogic(rdd: RDD[String]): Unit = {
    import spark.implicits._
    val rating: RDD[Rating] = rdd.map(csvRating => Rating(csvRating))
    val ratingDf = rating.toDF()
    movieDf
      .join(ratingDf, "mID")
      .coalesce(1)
      .write.mode(SaveMode.Append).json("/user/fall2019/iraj/movie_rating_json3")
//      .show()
  }
}
