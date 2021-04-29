package ca.dataedu.spark.core

import org.apache.spark.mllib.linalg.{Matrices, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint

object Spark16MLLabeledPointAndLocalMatrix extends App with Base {

  /*
  Labeled points are used for training data with label.
  Each point has two parts:
  - the label
  - a local vector (dense or sparse) representing of feature values
   */
  val p1 = LabeledPoint(15, Vectors.dense(1, 82))
  val p2 = LabeledPoint(25, Vectors.dense(2, 92.5))
  println(p1)
  println(p2)

  /*
  Local matrix could also be dense or sparse.
  Each matrix is defined by the number of rows and columns and then values as an
  array where data is stored in column-major order
   */
  /**
    * A 3 x 3 matrix:
    *
    * 1.0   25.0  4.2
    * 3.0   5.6   2.1
    * 0.0   8.2   9.0
    */
  val m1 =
    Matrices.dense(3, 3, Array(1.0, 3.0, 0.0, 25.0, 5.6, 8.2, 4.2, 2.1, 9.0))
  println(m1)

  /**
    * A 3 x 2 matrix:
    *
    * 1.0   25.0
    * 3.0   5.6
    * 0.0   8.2
    */
  val m2 =
    Matrices.dense(3, 2, Array(1.0, 3.0, 0.0, 25.0, 5.6, 8.2))
  println(m2)

  /**
    * A 3 x 3 sparse matrix
    *
    * 1 0 0
    * 0 0 2
    * 0 0 0
    */
  val m3 =
    Matrices.sparse(3, 3, Array(0, 2), Array(0, 1), Array(1.0, 2.0))
  println(m3)

}
