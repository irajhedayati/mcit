package ca.dataedu.spark.core

import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg._

object Spark14MLLocalVector extends App with Base {

  /*
    For the local vector and local matrix, we don't need a spark context or spark session.
   */

  /**
    * A dense vector has most of the elements non-zero
    * [15.0, 25.0, 0.0, 28.0, 41.0, 47.0, 50.0, 46.0, 37.0, 22.0]
    * */
  val vectorDense: linalg.Vector = Vectors.dense(15.0, 25.0, 0.0, 28.0, 41.0, 47.0, 50.0, 46.0, 37.0, 22.0)
  println(vectorDense)
  /**
    * A sparse vector has most of the elements as zero.
    *    0    1    2    3    4    5    6     7    8     9
    * [15.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 0.0, 0.0, 22.0]
    * */
  val vectorSparse1 = Vectors.dense(15.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 0.0, 0.0, 22.0)
  println(vectorSparse1)
  /**
    * We could improve storage requirements by just mentioning the non-zero values
    *
    *    0    1    2    3    4    5    6     7    8     9
    * [15.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 0.0, 0.0, 22.0]
    *
    * One way is to use two arrays:
    * - Index of non-zero values: [0, 6, 9]
    * - Actual values corresponding to the previous array: [15.0, 50.0, 22.9]
    * */
  val vectorSparse2 = Vectors.sparse(10, Array(0, 6, 9), Array(15.0, 50.0, 22.9))
  println(vectorSparse2)
  /**
    *    0    1    2    3    4    5    6     7    8     9
    * [15.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 0.0, 0.0, 22.0]
    *
    * Another way is to represent non-zero values each of which a pair of index and the value
    * [(0, 15.0), (6, 50.0), (9, 22.9)]
    * */
  val vectorSparse3 = Vectors.sparse(10, Seq((0, 15.0), (6, 50.0), (9, 22.9)))
  println(vectorSparse3)

}
