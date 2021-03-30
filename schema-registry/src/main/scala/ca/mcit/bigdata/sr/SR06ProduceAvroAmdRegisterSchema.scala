package ca.mcit.bigdata.sr

import ca.mcit.bigdata.sr.SR02RegisterSchema.getClass
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import io.confluent.kafka.serializers.{AbstractKafkaSchemaSerDeConfig, KafkaAvroSerializer}
import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericRecord, GenericRecordBuilder}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import java.io.InputStream
import java.util.Properties
import scala.io.Source

/**
  * Same as `SR05ProduceAvroGeneric` except:
  * - the Kafka topic name is `new.movie`
  * - the subject `new.movie-value` is not registered in Schema Registry
  * - hence, we parse the movie schema from the file
  */
object SR06ProduceAvroAmdRegisterSchema extends App {

  val kafkaTopicName = "new.movie"
  val movieSubjectName = "new.movie-value"

  // 1. prepare data
  val movieSrc = getClass.getResourceAsStream("/movie.csv")
  val movies: List[String] = Source.fromInputStream(movieSrc).getLines().toList.tail

  // 2. create schema
  val movieSchemaSource = getClass.getResourceAsStream("/movie.avsc")
  val movieSchemaStr = Source.fromInputStream(movieSchemaSource).getLines().mkString("\n")
  val movieSchema = new Schema.Parser().parse(movieSchemaStr)

  // 3. convert CSV records to Avro messages (GenericRecord)
  val movieRecords = movies
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
  /* If auto schema registration is not desired, use this configuration */
//  producerProperties.setProperty(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, "false")
  val producer = new KafkaProducer[String, GenericRecord](producerProperties)

  movieRecords
    .map(record => new ProducerRecord[String, GenericRecord](kafkaTopicName, record.get("mId").toString, record))
    .foreach(producer.send)

  producer.flush()
  producer.close()

}
