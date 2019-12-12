name := "kafka2"

version := "0.1"

scalaVersion := "2.12.10"

stringType in AvroConfig := "String"

resolvers ++= Seq(
  "Confluent" at "https://packages.confluent.io/maven",
  Resolver.mavenLocal
)

val avroVersion = "1.9.0"
val confluentVersion = "3.1.2"

libraryDependencies ++= Seq(
  "org.apache.avro"  % "avro"                         % avroVersion,
  "org.apache.avro"  % "avro-ipc"                     % avroVersion,
  "org.apache.avro"  % "avro-compiler"                % avroVersion,
  "io.confluent"     % "kafka-schema-registry-client" % confluentVersion,
  "io.confluent"     % "kafka-avro-serializer"        % confluentVersion,
  "org.apache.kafka" % "kafka-clients"                % "0.10.1.1",
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13"
)