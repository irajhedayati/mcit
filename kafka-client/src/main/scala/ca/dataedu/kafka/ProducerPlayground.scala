package ca.dataedu.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}

object ProducerPlayground extends App {

  val topicName = "test-1"

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

  producer.send(new ProducerRecord[Int, String](topicName, customPartitioner(10), 10, "Message 1"))
  producer.send(new ProducerRecord[Int, String](topicName, customPartitioner(20),20, "Message 2"))
  producer.send(new ProducerRecord[Int, String](topicName, customPartitioner(30),30, "Message 3"))
  producer.send(new ProducerRecord[Int, String](topicName, customPartitioner(40),40, "Message 4"))
  producer.send(new ProducerRecord[Int, String](topicName, customPartitioner(50),50, "Message 5"))
  producer.send(new ProducerRecord[Int, String](topicName, customPartitioner(60),60, "Message 6"))

  producer.flush() // producer.send works async and just to make sure all the messages are published

  def customPartitioner(key: Int): Int = key % 3
}
