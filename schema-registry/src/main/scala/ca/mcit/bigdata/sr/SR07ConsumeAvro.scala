package ca.mcit.bigdata.sr

import io.confluent.kafka.serializers.{AbstractKafkaSchemaSerDeConfig, KafkaAvroDeserializer}
import org.apache.avro.generic.GenericRecord

import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

import java.time.Duration
import scala.collection.JavaConverters._

object SR07ConsumeAvro extends App {

  // 1. prepare Kafka consumer properties
  val consumerProperties = new Properties()
  consumerProperties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  consumerProperties.setProperty(GROUP_ID_CONFIG, "movie-consumer")
  consumerProperties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
  /* What is this configuration for ('auto.offset.reset')? */
  consumerProperties.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest")
  consumerProperties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[KafkaAvroDeserializer].getName)
  consumerProperties.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081")

  // 2. instantiate a Kafka consumer
  val consumer = new KafkaConsumer[String, GenericRecord](consumerProperties)
  consumer.subscribe(List("movie").asJava)

  // 3. consume
  println("| Movie ID | Title | Year | Director |")
  while(true) {
    val polledRecords = consumer.poll(Duration.ofSeconds(1))
    if (!polledRecords.isEmpty) {
      val recordIterator = polledRecords.iterator()
      while(recordIterator.hasNext) {
        // val record = recordIterator.next()
        // println(s"| ${record.key()} | ${record.value()} | ${record.partition()} | ${record.offset()} |")
        val r = recordIterator.next().value()
        println(s"| ${r.get("mId")} | ${r.get("title")} | ${r.get("year")} | ${r.get("director")} |")
      }
    }
  }

}
