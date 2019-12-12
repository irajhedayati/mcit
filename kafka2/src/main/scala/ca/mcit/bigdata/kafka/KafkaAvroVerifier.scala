package ca.mcit.bigdata.kafka

import java.util.Properties

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.joda.time.DateTime
import scala.collection.JavaConverters._

object KafkaAvroVerifier extends App {
  val property: Properties = new Properties()
  property.put("bootstrap.servers", "172.16.129.58:9092")
  property.put("group.id", "iraj2")
  property.put("key.deserializer",   "org.apache.kafka.common.serialization.StringDeserializer")
  property.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer")
  property.put("auto.offset.reset",  "earliest")
  property.put("schema.registry.url", "http://172.16.129.58:8081")
  val consumer = new KafkaConsumer[String, GenericRecord](property)

  consumer.subscribe(List("iraj_enriched_movie").asJava)

  while(true) {
    println("Batch start => " + new DateTime())
    val polledRecords: ConsumerRecords[String, GenericRecord] = consumer.poll(1000)
    polledRecords.forEach(consumerRecord => {
      println(consumerRecord.value())
    })
    consumer.commitSync()
    Thread.sleep(4000)
  }
}
