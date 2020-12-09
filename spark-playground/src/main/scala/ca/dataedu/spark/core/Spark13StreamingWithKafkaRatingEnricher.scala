package ca.dataedu.spark.core

import ca.dataedu.spark.core.model.Rating
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Spark13StreamingWithKafkaRatingEnricher extends App with Base {

  val spark = SparkSession.builder().appName("Spark Streaming w/ Kafka").master("local[*]").getOrCreate()
  val ssc = new StreamingContext(spark.sparkContext, Seconds(10))

  /* ~~~~~~~~~~ Create Movie dataframe ~~~~~~~~~~ */
  val movieSchema: StructType = StructType(List(
    StructField("mId", IntegerType, nullable = false),
    StructField("title", StringType, nullable = false),
    StructField("year", IntegerType, nullable = false),
    StructField("director", StringType, nullable = true)
  ))
  val movieDF = spark.read.option("header", "true").schema(movieSchema).csv("/user/iraj/movie.csv")

  /* ~~~~~~~~~~ Stream Rating ~~~~~~~~~~ */
  val kafkaConfig: Map[String, String] = Map(
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.GROUP_ID_CONFIG -> "spark-streaming-with-kafka",
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest",
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> "false"
  )
  val kafkaStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
    ssc,
    LocationStrategies.PreferConsistent,
    ConsumerStrategies.Subscribe[String, String](List("rating"), kafkaConfig)
  )

  val kafkaStreamValues: DStream[String] = kafkaStream.map(_.value())

  kafkaStreamValues.foreachRDD(ratingRdd => processAndEnrich(ratingRdd))

  /* ~~~~~~~~~~~~~ Process, transform, enrich and load rating ~~~~~~~~~~~~~ */
  def processAndEnrich(ratingRdd: RDD[String]): Unit = {
    // 1. parse CSV rating to Scala object
    // 2. convert RDD to DataFrame
    // 3. join two dataframes
    // 4. write to HDFS
    import spark.implicits._
    val ratingDF = ratingRdd.map(Rating(_)).toDF
    ratingDF.join(movieDF, "mID").write.mode(SaveMode.Append).json("/user/iraj/enriched_rating")
  }

  ssc.start()
  ssc.awaitTermination()
  ssc.stop(stopSparkContext = true, stopGracefully = true)

}
