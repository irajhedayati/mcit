package ca.mcit.spark

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.SparkContext
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
  /**
    * We need a SparkContext to create a SparkStreamingContext.
    * Here we have two options:
    * - Create SparkContext as we did using Spark Core
    * - Create SparkSession using Spark SQL and then access
    *   the SparkContext wrapped by SparkSession
    */
    /* Using Spark Core*/
  /**
    *val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Test Spark")
    * val sc: SparkContext = new SparkContext(sparkConf)
    */
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Sample Spark SQL")
    .master("local[*]")
    .getOrCreate()
  val sc: SparkContext = spark.sparkContext

  val ssc: StreamingContext = new StreamingContext(sc, Seconds(5))

  // Create the stream
  val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
    ssc,
    LocationStrategies.PreferConsistent,
    ConsumerStrategies.Subscribe[String, String](Array(topic), kafkaParams)
  )
  // what to do?
  case class StopTime()
  val x: DStream[String] = stream.map(record => record.value())
  x.foreachRDD(microRdd => {
    // microRDD is the RDD created by DStream at each 5 seconds time interval from the input stream
    microRdd.map(_.split(",")).map(_(0)).take(10).foreach(println)
  })

  ssc.start()
  ssc.awaitTermination()

}
