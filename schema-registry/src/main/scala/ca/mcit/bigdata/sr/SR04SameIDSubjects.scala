package ca.mcit.bigdata.sr

import ca.mcit.bigdata.sr.SR02RegisterSchema.id
import io.confluent.kafka.schemaregistry.ParsedSchema
import io.confluent.kafka.schemaregistry.avro.AvroSchema
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import org.apache.avro.Schema

import java.io.InputStream
import scala.io.Source
import scala.collection.JavaConverters._

object SR04SameIDSubjects extends App {

  val srClient = new CachedSchemaRegistryClient("http://localhost:8081", 10)
  val movieSchemaFromFileSource: InputStream = getClass.getResourceAsStream("/movie.avsc")
  val movieSchemaFromFileStr: String = Source.fromInputStream(movieSchemaFromFileSource).getLines().mkString("\n")
  val movieSchemaFromFile: Schema = new Schema.Parser().parse(movieSchemaFromFileStr)

  val id = srClient.register("movie-value", new AvroSchema(movieSchemaFromFile).asInstanceOf[ParsedSchema])

  /** Guess what is the ID of movie2? */
  println(s"Registered 'movie-value' schema with ID $id")

  srClient.getAllSubjectsById(1).asScala.toList foreach println

}
