package ca.mcit.spark

import ca.mcit.spark.MainSQL.spark
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

object SparkStreamingKafka extends App {

    // Configuration
  val kafkaParams = Map[String, String] (
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.GROUP_ID_CONFIG -> "test",
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest",
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> "false"
  )
  val topic = "stop_times"

  // Setup
//  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Test Spark")
//  val sc: SparkContext = new SparkContext(sparkConf)
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Sample Spark SQL")
    .master("local[*]")
    .getOrCreate()
  val ssc: StreamingContext = new StreamingContext(spark.sparkContext, Seconds(5))

  val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
    ssc,
    LocationStrategies.PreferConsistent,
    ConsumerStrategies.Subscribe[String, String](Array(topic), kafkaParams)
  )

  // Read big dataframe
  val enrichedTripDF = spark
    .read
    .format("csv")
    .option("header","true")
    .load("/user/cloudera/iraj/stm/enriched_trip")

  // what to do?
  import spark.implicits._
  case class StopTime()
  val x: DStream[String] = stream.map(record => record.value())
  x.foreachRDD(microRdd => {

    microRdd
      .map(_.split(",")).map(r => StopTime()).toDF
      .join(enrichedTripDF)
      .write.csv("/user/cloudera/iraj/stm/enriched_stop_times")

    microRdd.map(_.split(",")).map(_(0)).take(10).foreach(println)
  })

  ssc.start()
  ssc.awaitTermination()

}
