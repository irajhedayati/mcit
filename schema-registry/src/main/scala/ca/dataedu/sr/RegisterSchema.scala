package ca.dataedu.sr

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import org.apache.avro.Schema

import scala.io.Source

object RegisterSchema extends App {

  // 1. Read the schema from a file
  val movieSchemaText = Source.fromString(
    """{
      |  "type": "record",
      |  "name": "Movie",
      |  "namespace": "ca.dataedu.avro",
      |  "fields": [
      |    { "name":  "mID", "type": "int" },
      |    { "name":  "title", "type": "string" },
      |    { "name":  "year", "type": "int" },
      |    {
      |      "name": "director",
      |      "type": [ "null", "string" ],
      |      "default": null
      |    }
      |  ]
      |}""".stripMargin
  )
    .mkString
  // 2. Parse schema
  val movieSchema = new Schema.Parser().parse(movieSchemaText)

  // 3. Create a client
  val srClient = new CachedSchemaRegistryClient("http://localhost:8081", 1)
  srClient.register("movie", movieSchema)

}
