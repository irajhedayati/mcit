name := "spark-playground"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.4.4."

//libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.3.3"
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion
