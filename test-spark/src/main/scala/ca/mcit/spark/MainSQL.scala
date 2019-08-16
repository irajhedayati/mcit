package ca.mcit.spark

import org.apache.spark.sql.SparkSession

/**
  * This application uses Spark SQL API
  */
object MainSQL extends App {

  // Prepare Spark Session
  // Note that here we use a builder but internally, each spark session
  // wraps a SparkConfig and a SparkContext
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Sample Spark SQL")
    .master("local[*]")
    .getOrCreate()

  // Read files from HDFS
  /** Note that Hadoop configurations should be
    * in the class path to enabled Spark read from HDFS. Otherwise,
    * it will switch to local filesystem by default.
    * If you are running in Intellij Idea,
    * - Open "File" menu
    * - Select "Project Structure"
    * - From the left pane, select "modules"
    * - From the right pane, select "Dependencies"
    * - In the right side, you see a "+" sign, click and select 
    *
    */
  val trips = spark.read
    .format("csv")
    .option("header","true")
    .load("/user/cloudera/iraj/stm/trips.txt")
  val frequencies = spark.read
    .format("csv")
    .option("header","true")
    .load("/user/cloudera/iraj/stm/frequencies.txt")

  trips.show()
  frequencies.show()

  trips.printSchema()
  frequencies.printSchema()

  spark.close()
}
