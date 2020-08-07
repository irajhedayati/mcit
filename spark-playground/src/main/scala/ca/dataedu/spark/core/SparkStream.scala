package ca.dataedu.spark.core

import org.apache.spark.streaming.dstream.{DStream, InputDStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object SparkStream extends App {

  // 1. create streaming context
  // If using spark-submit script, the master should not be set
  val sparkConf = new SparkConf()
    .setAppName("Practice Spark Streaming")
    .setMaster("local[2]")
  val sc = new SparkContext(sparkConf)
  val ssc: StreamingContext = new StreamingContext(sc, Seconds(5))

  // 2. source
//  val lines: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)
  val lines: DStream[String] =
    ssc.textFileStream("/user/iraj/emp_dept.json/")

  // 3. business logic
//  lines.print()
  lines.foreachRDD( rdd => {
    // at this point, it is just Spark engine e.g. Spark Core, Spark ML, etc.
    // Word count
    rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
      .take(10).foreach(println)

  })

  // 4. cleanup
  ssc.start()
  ssc.awaitTermination()
}
