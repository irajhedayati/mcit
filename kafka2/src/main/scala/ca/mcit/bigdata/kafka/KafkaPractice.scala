package ca.mcit.bigdata.kafka

import java.util.Properties

import collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.joda.time.DateTime

object KafkaPractice extends App {

  val property: Properties = new Properties()
  property.put("bootstrap.servers", "172.16.129.58:9092")
  property.put("group.id", "iraj")
  property.put("key.deserializer",   "org.apache.kafka.common.serialization.StringDeserializer")
  property.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  property.put("auto.offset.reset",  "earliest")
  val consumer = new KafkaConsumer[String, String](property)

  val producerProp: Properties = new Properties()
  producerProp.put("bootstrap.servers", "172.16.129.58:9092")
  producerProp.put("key.serializer",   "org.apache.kafka.common.serialization.StringSerializer")
  producerProp.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](producerProp)

  consumer.subscribe(List("iraj_movie").asJava)

  while(true) {
    println("Batch start => " + new DateTime())
    val polledRecords: ConsumerRecords[String, String] = consumer.poll(1000)
    polledRecords.forEach(consumerRecord => {
      val producedMessage = new ProducerRecord[String, String](
        "iraj_movie_transformed",
        "p_" + consumerRecord.value()) // transformation
      producer.send(producedMessage)
    })
    consumer.commitSync()
    Thread.sleep(4000)
  }

}
