package ca.dataedu.spark.core

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Spark18MLLogisticRegression extends App with Base {

  val spark = SparkSession.builder().appName("Spark ML").master("local[*]").getOrCreate()
  /*
  Labeled data (7 entries): we divide it in two set
  =================================
  1. Training dataset (4 entries) to create the model
  0.0  1.1 0.1       => 1
  2.0  1.0 -1.0      => 0
  2.0  1.3 1.0       => 0
  0.0  1.2 -0.5      => 1

  2. Test dataset (3 entries) to evaluate the model
  -1.0 1.5 1.3
  3.0  2.0 -0.1
  0.0  2.2 -1.5
  */

  val training: DataFrame = spark.createDataFrame(Seq(
    (1.0, Vectors.dense(0.0, 1.1, 0.1)),
    (0.0, Vectors.dense(2.0, 1.0, -1.0)),
    (0.0, Vectors.dense(2.0, 1.3, 1.0)),
    (1.0, Vectors.dense(0.0, 1.2, -0.5))
  )).toDF("label", "features")

  val lr = new LogisticRegression()
  val model = lr.fit(training)

  val testData = spark.createDataFrame(Seq(
    (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
    (0.0, Vectors.dense(3.0, 2.0, -0.1)),
    (1.0, Vectors.dense(0.0, 2.2, -1.5))
  )).toDF("label", "features")

  model.transform(testData)
    .select("features", "label", "probability", "prediction")
    .collect()
    .foreach { case Row(features, label: Double, prob, prediction: Double) =>
      println(s"($features, $label) -> prob=$prob, prediction=$prediction")
    }

  spark.stop()

}
