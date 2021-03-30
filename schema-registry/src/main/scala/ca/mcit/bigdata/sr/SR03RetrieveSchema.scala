package ca.mcit.bigdata.sr

import io.confluent.kafka.schemaregistry.ParsedSchema
import io.confluent.kafka.schemaregistry.client.{CachedSchemaRegistryClient, SchemaMetadata}

object SR03RetrieveSchema extends App {

  // 1- Connect to Schema Registry
  val srClient = new CachedSchemaRegistryClient("http://localhost:8081", 10)

  // 2- Retrieve schema (most of the time, we use this method)
  // 2.a- retrieve the ID of the latest version of the schema of the subject
  val movieSubjectMetadata: SchemaMetadata = srClient.getLatestSchemaMetadata("movie")
  println(s"Version: ${movieSubjectMetadata.getVersion}")
  println(s"ID: ${movieSubjectMetadata.getId}")
  // 2.b- retrieve the schema by ID which is a unique number
  val movieSchema: ParsedSchema = srClient.getSchemaById(movieSubjectMetadata.getId)

  // 3- use the schema
  println(movieSchema)


}
