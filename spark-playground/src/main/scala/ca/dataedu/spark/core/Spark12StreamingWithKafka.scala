package ca.dataedu.spark.core

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Spark12StreamingWithKafka extends App {

  // 1. create streaming context
  // If using spark-submit script, the master should not be set
  val sparkConf = new SparkConf()
    .setAppName("Practice Spark Streaming")
    .setMaster("local[2]")
  val sc = new SparkContext(sparkConf)
  val ssc: StreamingContext = new StreamingContext(sc, Seconds(5))

  // 2. source
  val kafkaConfig = Map[String, String] (
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.GROUP_ID_CONFIG -> "test",
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest",
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> "false"
  )
  val topic = "spark_streaming_test"
  val messages: InputDStream[ConsumerRecord[String, String]] =
    KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](List(topic), kafkaConfig)
    )
  val lines: DStream[String] = messages.map(_.value())

//  lines.print()
  // 3. business logic
  lines.foreachRDD( rdd => {
    // at this point, it is just Spark engine e.g. Spark Core, Spark ML, etc.
    // Word count
    rdd.flatMap(_.split(" ")).take(10).foreach(println)
//      .take(10).foreach(println)
//
  })

  // 4. cleanup
  ssc.start()
  ssc.awaitTermination()
}
