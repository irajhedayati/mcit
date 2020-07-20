package ca.dataedu.spark.core

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object SparkCoreRdd extends App {

  // 0. Spark configuration
  /**
    * Master: if only running in IDE; shouldn't be set in production
    * - local[n]: n is the number of cores to use
    * - local[*]: use all the cores
    * - yarn-client
    * - yarn-cluster
    */
  val sparkConf = new SparkConf()
    .setAppName("Spark Core practice")
    .setMaster("local[*]")

  // 1. Spark Context
  val sc: SparkContext = new SparkContext(sparkConf)

  // 2. Business logic
  // 2.1 create RDD (1000 random numbers)
  val x0 = sc.parallelize((1 to 1000).map(_ => Random.nextInt))
  println(s"Number of partitions ${x0.getNumPartitions}")
  x0.foreachPartition(x => println(s"====> ${x.toList.size}"))

  // Change the number of partitions
//  val x1 = x0.repartition(4)
  val x1 = x0.coalesce(4)
  x1.foreachPartition(x => println(s"====> ${x.toList.size}"))
  // 2.2 apply transformation + an action to start working
  val x2 = x0.map(_ * 2).take(10)
  x2 foreach println

  // 2.3 add a second job
  val x3 = x0.filter(_ < 100).coalesce(4)
  x3.foreachPartition(x => println(s"====> ${x.toList.size}"))
  val x4 = x3.count()
  println(s"====> $x4 numbers are less than 100")
  // Just to keep it alive to check UI

  while(true) Thread.sleep(1000)

  // 3. stop the context
  sc.stop()
}
