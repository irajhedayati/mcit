package ca.dataedu.kafka

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.{IntegerDeserializer, StringDeserializer}

import scala.collection.JavaConverters._

object ConsumerPlayground extends App {

  val topicName = "test-1"

  val consumerProperties = new Properties()
  consumerProperties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  consumerProperties.setProperty(GROUP_ID_CONFIG, "group-id-1")
  consumerProperties.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest")
  consumerProperties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, classOf[IntegerDeserializer].getName)
  consumerProperties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
  consumerProperties.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "false")

  val consumer = new KafkaConsumer[Int, String](consumerProperties)
  consumer.subscribe(List(topicName).asJava)

  println("| Key | Message | Partition | Offset |")
  while (true) {
    val polledRecords: ConsumerRecords[Int, String] = consumer.poll(Duration.ofSeconds(1))
    if (!polledRecords.isEmpty) {
      println(s"Polled ${polledRecords.count()} records")
      val recordIterator = polledRecords.iterator()
      while (recordIterator.hasNext) {
        val record = recordIterator.next()
        println(s"| ${record.key()} | ${record.value()} | ${record.partition()} | ${record.offset()} |")
      }
      consumer.commitSync(Duration.ofSeconds(1))
    }
  }
}
