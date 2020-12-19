package ca.dataedu.sr

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient

import scala.collection.JavaConverters._

object RetrieveSchema extends App {

  // 1. Create Schema Registry client
  val srClient = new CachedSchemaRegistryClient("http://localhost:8081", 3)
  // 2. Get the list of registered subjects
  srClient.getAllSubjects.asScala.foreach(println)
  // 3. Retrieve schema of a subject
  // 3.a  Get the ID of a specific version
  val metadata = srClient.getSchemaMetadata("person", 1)
  val schemaId = metadata.getId
  // 3.b  Retrieve schema using the ID
  val schema = srClient.getByID(schemaId)
  println(schema.toString(true))

}
