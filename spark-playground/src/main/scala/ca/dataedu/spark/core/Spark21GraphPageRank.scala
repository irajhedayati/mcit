package ca.dataedu.spark.core

import org.apache.spark.graphx.{Graph, GraphLoader, VertexRDD}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark21GraphPageRank extends App with Base {

  val sparkConf = new SparkConf().setAppName("Spark Core practice").setMaster("local[*]")
  val sc: SparkContext = new SparkContext(sparkConf)

  val graph: Graph[Int, Int] = GraphLoader.edgeListFile(sc, "/user/iraj/followers.txt")
  val ranks: VertexRDD[Double] = graph.pageRank(0.0001).vertices

  val users: RDD[(Long, String)] = sc.textFile("/user/iraj/users.txt")
    .map(_.split(","))
    .map(fields => (fields(0).toLong, fields(1)))

  val ranksByUserName: RDD[(String, Double)] = users.join(ranks).map {
    case (id, (username, rank)) => (username, rank)
  }

  ranksByUserName.take(20).foreach(println)

  sc.stop()

}
