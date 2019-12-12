package ca.mcit.bigdata.kafka

import java.util.Properties

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.joda.time.DateTime

import scala.collection.JavaConverters._

object KafkaPractice extends App {

  val property: Properties = new Properties()
  property.put("bootstrap.servers", "172.16.129.58:9092")
  property.put("group.id", "iraj2")
  property.put("key.deserializer",   "org.apache.kafka.common.serialization.StringDeserializer")
  property.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  property.put("auto.offset.reset",  "earliest")
  val consumer = new KafkaConsumer[String, String](property)

  val producerProp: Properties = new Properties()
  producerProp.put("bootstrap.servers",   "172.16.129.58:9092")
  producerProp.put("key.serializer",      "org.apache.kafka.common.serialization.StringSerializer")
  producerProp.put("value.serializer",    "io.confluent.kafka.serializers.KafkaAvroSerializer")
  producerProp.put("schema.registry.url", "http://172.16.129.58:8081")
  val producer = new KafkaProducer[String, GenericRecord](producerProp)

  consumer.subscribe(List("iraj_movie_v2").asJava)

  while(true) {
    println("Batch start => " + new DateTime())
    val polledRecords: ConsumerRecords[String, String] = consumer.poll(1000)
    polledRecords.forEach(consumerRecord => {
      println(consumerRecord.value())
      // parse the source messages
      val movieFields: Array[String] = consumerRecord.value().split(",", -1)
      val movie = Movie
        .newBuilder()
        .setMid(movieFields(0).toInt)
        .setTitle(movieFields(1))
        .setYear(movieFields(2).toInt)
        .setDirector(movieFields(3))
        .build()
      // generate final messages
      val enrichedMovie = EnrichedMovie
        .newBuilder()
        .setMovie(movie)
        .setRatings(List().asJava)
        .build()
      println(enrichedMovie)
      val producedMessage = new ProducerRecord[String, GenericRecord](
        "iraj_enriched_movie",
        enrichedMovie)
      producer.send(producedMessage)
    })
    consumer.commitSync()
    Thread.sleep(4000)
  }

}
