package ca.dataedu.spark.core

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.types.{DoubleType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Spark18MLLogisticRegression extends App with Base {

  val spark = SparkSession.builder().appName("Spark ML").master("local[*]").getOrCreate()
  /*
  Input data is labeled
  Feature columns: Pregnancies,Glucose,BloodPressure,SkinThickness,Insulin,BMI,DiabetesPedigreeFunction,Age
  Label: Outcome
  */

  import spark.implicits._

  val schema = StructType(List(
    StructField("Pregnancies", DoubleType),
    StructField("Glucose", DoubleType),
    StructField("BloodPressure", DoubleType),
    StructField("SkinThickness", DoubleType),
    StructField("Insulin", DoubleType),
    StructField("BMI", DoubleType),
    StructField("DiabetesPedigreeFunction", DoubleType),
    StructField("Age", DoubleType),
    StructField("Outcome", DoubleType)
  ))

  val input: DataFrame = spark.read.option("header", "true")
    .schema(schema)
    // Read training data
    .csv("/Users/iraj.hedayati/codes/mcit/spark-playground/data/diabetes.csv")
    // Select features and labels
    .select("Pregnancies", "Insulin", "BMI", "Age", "Glucose", "BloodPressure", "DiabetesPedigreeFunction", "Outcome")
    .rdd
    .map { row =>
      val featuresVector =
        Vectors.dense(
          row.getDouble(0),
          row.getDouble(1),
          row.getDouble(2),
          row.getDouble(3),
          row.getDouble(4),
          row.getDouble(5),
          row.getDouble(6)
        )
      row.getDouble(7) -> featuresVector
    }
    .toDF("label", "features")

  input.randomSplit(Array(0.8, 0.2)) match {
    case Array(trainingDataset, testDataset) =>
      val lr = new LogisticRegression()
      val model = lr.fit(trainingDataset)
      model.transform(testDataset)
        .select("features", "label", "probability", "prediction")
        .collect()
        .foreach { case Row(features, label: Double, prob, prediction: Double) =>
          println(s"($features, $label) -> prob=$prob, prediction=$prediction")
        }
  }

  spark.stop()

}
