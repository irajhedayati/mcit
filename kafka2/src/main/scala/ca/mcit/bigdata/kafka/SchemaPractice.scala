package ca.mcit.bigdata.kafka

import io.confluent.kafka.schemaregistry.client.{CachedSchemaRegistryClient, SchemaRegistryClient}

object SchemaPractice extends App {
  println(Movie.SCHEMA$.toString(true))

  val schemaRegistryClient: SchemaRegistryClient =
    new CachedSchemaRegistryClient("http://172.16.129.58:8081", 1)
  schemaRegistryClient.getAllSubjects.forEach(println)
  /*
  val irajSchemaMetadata = schemaRegistryClient.getLatestSchemaMetadata("iraj_test")
  println(s"id: ${irajSchemaMetadata.getId}")
  println(s"version: ${irajSchemaMetadata.getVersion}")
  println(irajSchemaMetadata.getSchema)

  val irajSchema =
    schemaRegistryClient.getBySubjectAndID("iraj_test", irajSchemaMetadata.getId)
  println(irajSchema.toString(true))
   */
  schemaRegistryClient.register("iraj_enriched_movie-value", EnrichedMovie.SCHEMA$)
}
