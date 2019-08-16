package ca.mcit.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object MainStreaming extends App {

  // Setup
  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Test Spark")
  val sc: SparkContext = new SparkContext(sparkConf)
  val ssc: StreamingContext = new StreamingContext(sc, Seconds(5))

  // Source
  //val lines: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)
  val lines = ssc.textFileStream("/user/cloudera/iraj/stm")

  // What to do?
  val words: DStream[String] = lines.flatMap(_.split(" "))
  val wordCounts: DStream[(String, Int)] = words.map((_, 1)).reduceByKey(_ + _)
  wordCounts.print()

  // Start
  ssc.start()
  ssc.awaitTermination()

}
