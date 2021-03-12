name := "hive-playground"

version := "0.1"

scalaVersion := "2.11.8"

resolvers += "Cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

val HadoopVersion = "2.7.3"
val HiveJdbcVersion = "1.1.0-cdh5.16.2"

val hiveDeps = Seq(
  "org.apache.hive" % "hive-jdbc" % HiveJdbcVersion
)

val hdfsDeps = Seq(
  "org.apache.hadoop" % "hadoop-common" % HadoopVersion,
  "org.apache.hadoop" % "hadoop-hdfs"   % HadoopVersion
)

libraryDependencies ++= hiveDeps ++ hdfsDeps
