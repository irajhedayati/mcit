name := "test-spark"

version := "0.1"

scalaVersion := "2.11.8"
/*
Spark  2.3.3
Hadoop 2.6.5
Scala  2.11
Avro   1.7.7
 */

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.3"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.3"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.3.3"

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.3.3"
