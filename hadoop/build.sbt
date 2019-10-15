name := "hadoop"

version := "0.1"

scalaVersion := "2.11.0"

val hadoopVersion = "2.6.0"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % hadoopVersion
libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion
