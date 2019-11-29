name := "kafka2"

version := "0.1"

scalaVersion := "2.12.10"

stringType in AvroConfig := "String"

resolvers ++= Seq(
  "Confluent" at "https://packages.confluent.io/maven",
  Resolver.mavenLocal
)

libraryDependencies ++= Seq(
  "org.apache.avro"  % "avro"                         % "1.9.0",
  "org.apache.avro"  % "avro-ipc"                     % "1.9.0",
  "org.apache.avro"  % "avro-compiler"                % "1.9.0",
  "io.confluent"     % "kafka-schema-registry-client" % "3.1.2",
  "org.apache.kafka" % "kafka-clients"                % "0.10.1.1"

)