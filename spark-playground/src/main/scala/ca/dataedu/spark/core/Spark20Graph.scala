package ca.dataedu.spark.core

import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark20Graph extends App with Base {

  val sparkConf = new SparkConf().setAppName("Spark Core practice").setMaster("local[*]")
  val sc: SparkContext = new SparkContext(sparkConf)

  // Create an RDD for the vertices
  val vertices: RDD[(VertexId, (String, String))] =
    sc.parallelize(Seq(
      (3L, ("rxin", "student")),
      (7L, ("jgonzal", "postdoc")),
      (5L, ("franklin", "prof")),
      (2L, ("istoica", "prof"))
    ))
  // Create an RDD for edges
  val edges: RDD[Edge[String]] =
    sc.parallelize(Seq(
      Edge(3L, 7L, "collab"),
      Edge(7L, 3L, "collab"),
//      Edge(5L, 3L, "advisor"),
      Edge(2L, 5L, "colleague"),
      Edge(5L, 2L, "colleague"),
      Edge(5L, 7L, "pi"),
      Edge(7L, 5L, "pi")
    ))

  /*
  triplets right join vertices
   */

  /*
  Edge: 1 -> 3
   */
  val defaultVertex = ("John Doe", "Missing")

  val graph = Graph(vertices, edges, defaultVertex)

  val postdocCount = graph.vertices.filter { case (id, (name, pos)) => pos == "postdoc" }.count()
  println(postdocCount)

  val facts = graph.triplets.map(triplet =>
    s"${triplet.srcAttr._1} is the ${triplet.attr} of ${triplet.dstAttr._1}"
  )
  facts.collect.foreach(println)

  sc.stop()

}
