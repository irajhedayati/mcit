package ca.dataedu.spark.core

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Spark13StreamingWithKafka extends App {

  // 1. Create Spark streaming context
  // 1.a A Spark configuration
  val conf = new SparkConf().setMaster("local[*]").setAppName("Spark streaming with Kafka")
  // 1.b A Spark context
  val sc = new SparkContext(conf)
  // 1.c A Spark streaming context
  val ssc = new StreamingContext(sc, Seconds(10))

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
  val topic = "test"
  val inStream: DStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
    ssc,
    LocationStrategies.PreferConsistent,
    ConsumerStrategies.Subscribe[String, String](List(topic), kafkaConfig)
  )

  // 3. Business logic for each micro-batch (a micro-batch is an RDD)
  // my business logic is to implement word count
  inStream.map(_.value()).foreachRDD(microBatchRdd => businessLogic(microBatchRdd))

  // 4. Start streaming and keep running
  ssc.start()
  ssc.awaitTermination()
  ssc.stop(stopSparkContext = true, stopGracefully = true)

  /** Calculate the word count */
  def businessLogic(rdd: RDD[String]): Unit = {
    rdd.flatMap(_.split(" ")).map(w => w -> 1).reduceByKey(_ + _).take(10).foreach {
      case (w, c) => println(s"Word '$w' is repeated $c times")
    }
  }

}
