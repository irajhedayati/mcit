package ca.mcit.bigdata.sr

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import io.confluent.kafka.serializers.{AbstractKafkaSchemaSerDeConfig, KafkaAvroSerializer}
import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericRecord, GenericRecordBuilder}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import java.util.Properties
import scala.io.Source

object SR05ProduceAvroGeneric extends App {

  val movieSubjectName = "movie-value"

  // 1. prepare data
  val movieSrc = getClass.getResourceAsStream("/movie.csv")
  val movies: List[String] = Source.fromInputStream(movieSrc).getLines().toList.tail

  // 2. retrieve the Avro schema from Schema Registry
  val srClient = new CachedSchemaRegistryClient("http://localhost:8081", 10)
  val movieMetadata = srClient.getLatestSchemaMetadata(movieSubjectName)
  val movieSchema = srClient.getSchemaById(movieMetadata.getId).rawSchema().asInstanceOf[Schema]

  // 3. convert CSV records to Avro messages (GenericRecord)
  val movieRecords: List[GenericData.Record] = movies
    .map(_.split(",", -1))
    .map { fields =>
      new GenericRecordBuilder(movieSchema)
        .set("mId", fields(0).toInt)
        .set("title", fields(1))
        .set("year", fields(2).toInt)
        .set("director", if (fields(3).nonEmpty) fields(3) else null)
        .build()
    }

  // 4. Produce to kafka
  val producerProperties = new Properties()
  producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
  producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer].getName)
  producerProperties.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081")
  val producer = new KafkaProducer[String, GenericRecord](producerProperties)

  movieRecords
    .map(record => new ProducerRecord[String, GenericRecord]("movie", record.get("mId").toString, record))
    .foreach(producer.send)

  producer.flush()
  producer.close()

}
