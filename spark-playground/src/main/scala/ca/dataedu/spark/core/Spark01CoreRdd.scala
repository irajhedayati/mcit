package ca.dataedu.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object Spark01CoreRdd extends App with Base {

  // 0. Spark configuration
  /**
    * Master: if only running in IDE; shouldn't be set in production
    * - local[n]: n is the number of cores to use
    * - local[*]: use all the cores
    * - yarn-client
    * - yarn-cluster
    */
  val sparkConf = new SparkConf().setAppName("Spark Core practice").setMaster("local[*]")
  // 1. Spark Context
  val sc: SparkContext = new SparkContext(sparkConf)

  // 2. Business logic
  // 2.1 create RDD (1000 random numbers)
  val randomNumbers = (1 to 1000).map(_ => Random.nextInt(1000))
  val rdd0: RDD[Int] = sc.parallelize(randomNumbers)
  println(s"Number of Partitions: ${rdd0.getNumPartitions}")
  rdd0.foreachPartition(partitionMembers => println(s"===> ${partitionMembers.toList.size}"))

  // 2.2 Change number of partitions
  // val rdd1 = rdd0.repartition(4) // it causes shuffle
  val rdd1 = rdd0.coalesce(4)
  println(s"Number of Partitions: ${rdd1.getNumPartitions}")
  rdd1.foreachPartition(partitionMembers => println(s"===> ${partitionMembers.toList.size}"))

  // 2.3 transformation + action
  val sampleOfRandomNumbers = rdd1.map(_ * 2).take(10)
  sampleOfRandomNumbers.foreach(println)

  // 2.4 filter
  println("~~~~~~~~~~~~ 2.4 filter ~~~~~~~~~~~~")
  val numbersLessThan100 = rdd0.filter(_ < 100).coalesce(4)
  numbersLessThan100.foreachPartition(partitionMembers => println(s"===> ${partitionMembers.toList.size}"))

  while (true) Thread.sleep(1000)

  // 100. stop the context
  sc.stop()
}
