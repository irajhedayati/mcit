package ca.dataedu.spark.core

import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.{IndexedRow, IndexedRowMatrix, RowMatrix}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark16MLDistributedMatrix extends App with Base {

  /**
    * Distributed matrix is an RDD of vectors. Hence, we need a spark context
    */
  val conf = new SparkConf().setAppName("Spark ML playground").setMaster("local[*]")
  val sc = new SparkContext(conf)

  /** ************ RowMatrix is just RDD of local vectors ************* */
  /**
    * 82.0   97.7
    * 92.5   131.9
    * 83.2   141.3
    */
  val vectors = Seq(
    Vectors.dense(97.7, 82.0),
    Vectors.dense(131.9, 92.5),
    Vectors.dense(141.3, 83.2)
  )
  val featuresRdd: RDD[linalg.Vector] = sc.parallelize(vectors)
  val rowMatrix = new RowMatrix(featuresRdd)
  println(s"${rowMatrix.numRows()} x ${rowMatrix.numCols()}")

  /** ************ IndexedRowMatrix is a RowMatrix that has indices ******* */
  /**
    * 0   -> 82.0   97.7
    * 1   -> 92.5   131.9
    * 2   -> 83.2   141.3
    */
  val indexedVectors = Seq(
    IndexedRow(0L, Vectors.dense(97.7, 82.0)),
    IndexedRow(1L, Vectors.dense(131.9, 92.5)),
    IndexedRow(2L, Vectors.dense(141.3, 83.2))
  )
  val indexedFeaturesRdd: RDD[IndexedRow] =sc.parallelize(indexedVectors)
  val indexedRowMatrix = new IndexedRowMatrix(indexedFeaturesRdd)
  println(s"${indexedRowMatrix.numRows()} x ${indexedRowMatrix.numCols()}")

  sc.stop()

}
