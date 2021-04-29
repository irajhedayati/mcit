package ca.dataedu.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Spark10StreamingState extends App with Base {
  // 1. Spark conf
  val sparkConf = new SparkConf().setAppName("Spark streaming practices").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val ssc = new StreamingContext(sc, Seconds(10))

  // 2. Create the stream (DStream)
  val networkSocketStream: DStream[String] = ssc.socketTextStream("localhost", 9999)
  // 3. Transform stream to a pair (key-value)
  val words: DStream[String] = networkSocketStream.flatMap(_.split(" "))
  val wordDStream: DStream[(String, Int)] = words.map(x => (x, 1))

  // 4. define update state function
  def updateFunc(): (Seq[Int], Option[Int]) => Some[Int] = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.sum
    val previousCount = state.getOrElse(0)
    Some(currentCount + previousCount)
  }

  // 5. Apply the function
  val stateDStream = wordDStream.updateStateByKey[Int](updateFunc())
  // 6. do something with result
  stateDStream.print()

  // 7. Start the streaming and keep it running
  ssc.start()
  ssc.awaitTermination()

}
