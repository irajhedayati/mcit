package ca.dataedu.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Spark11StreamingTextFile extends App with Base {

  // 1. Spark conf
  val sparkConf = new SparkConf().setAppName("Spark streaming practices").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val ssc = new StreamingContext(sc, Seconds(10))

  // 2. Create the stream (DStream)
  val fileStream: DStream[String] = ssc.textFileStream("/user/fall2019/iraj/stream_input/")

  // 3. Business logic
  // networkSocketStream.print()
  fileStream.foreachRDD(rdd => businessLogic(rdd))

  // 4. Start the streaming and keep it running
  ssc.start()
  ssc.awaitTermination()
  ssc.stop(stopSparkContext = true, stopGracefully = true)

  /** Calculate the word count */
  def businessLogic(rdd: RDD[String]): Unit = {
    rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
      .take(10).foreach(println)
  }

}
