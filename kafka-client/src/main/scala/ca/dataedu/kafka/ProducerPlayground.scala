package ca.dataedu.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}

object ProducerPlayground extends App {

  val topicName = "test_2"

  val producerProperties = new Properties()
  producerProperties.setProperty(
    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"
  )
  producerProperties.setProperty(
    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[IntegerSerializer].getName
  )
  producerProperties.setProperty(
    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName
  )

  val producer = new KafkaProducer[Int, String](producerProperties)

  producer.send(new ProducerRecord[Int, String](topicName, 10, "10,S1"))
  producer.send(new ProducerRecord[Int, String](topicName, 20, "20,S2"))
  producer.send(new ProducerRecord[Int, String](topicName, 30, "30,S3"))
  producer.send(new ProducerRecord[Int, String](topicName, 40, "40,S4"))
  producer.send(new ProducerRecord[Int, String](topicName, 50, "50,S5"))
  producer.send(new ProducerRecord[Int, String](topicName, 60, "60,S6"))

  producer.flush()

  def customPartitioned(key: Int): Int = key % 3
}
