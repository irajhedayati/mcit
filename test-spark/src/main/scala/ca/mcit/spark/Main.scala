package ca.mcit.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * This application is using Spark Core only
  */
object Main extends App {

  // Prepare Spark Context
  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Test Spark")
  val sc: SparkContext = new SparkContext(sparkConf)

  // Implement your application logic here
  val x: Double =
    sc
      /* Create an RDD of integer with 4 partitions (source) */
      .parallelize( 1 to 1000, 4)
      // Double the numbers and filter those less than 100 (Transformations)
      /**
        * The original notation of a Scala anonymous notation
        * .map( (x) => {
        *                 val y = x * 2
        *                 y
        *              }
        * )
        * That could be shorten as:
        */
      .map(_ * 2)
      .filter(_ < 100)

      // Aggregate RDD using sum function
      .sum()
  println(x)

  // Stop the context
  sc.stop()

}
