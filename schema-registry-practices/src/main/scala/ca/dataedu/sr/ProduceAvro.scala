package ca.dataedu.sr

import java.util.Properties

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.generic.{GenericRecord, GenericRecordBuilder}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import scala.io.Source

object ProduceAvro extends App {

  // 1. Data in CSV format (2 records)
  val movies: List[String] = Source.fromString(
    """105,Titanic,1997,James Cameron
      |106,Snow White,1937,""".stripMargin
  ).getLines().toList
  // 2. Get the schema of movie from Schema Registry
  val srClient = new CachedSchemaRegistryClient("http://localhost:8081", 1)
  val metadata = srClient.getSchemaMetadata("movie", 1)
  val movieSchema = srClient.getByID(metadata.getId)

  // 3. Convert CSV to Avro
  val avroMovies: List[GenericRecord] = movies.map { csvMovie =>
    val fields = csvMovie.split(",", -1)
    new GenericRecordBuilder(movieSchema)
      .set("mID", fields(0).toInt)
      .set("title", fields(1))
      .set("year", fields(2).toInt)
      .set("director", if (fields.size == 4) fields(3) else null) // director could be null
      .build()
  }

  // 4. Produce to Kafka
  // 4.a. Create producer properties that support Avro
  // 4.b. Create a producer
  // 4.c. Produce messages to Kafka
  val producerProperties = new Properties()
  producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
  producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getName)
  producerProperties.setProperty("schema.registry.url", "http://localhost:8081")

  val producer = new KafkaProducer[String, GenericRecord](producerProperties)

  avroMovies
    .map(avroMessage => new ProducerRecord[String, GenericRecord]("movie", avroMessage.get("mID").toString, avroMessage))
    .foreach(producer.send)

  producer.flush()

}
