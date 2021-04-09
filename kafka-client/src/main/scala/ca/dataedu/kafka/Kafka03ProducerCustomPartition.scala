package ca.dataedu.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}

object ProducerPlayground extends App {

  val producerProperties = new Properties()
  producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  producerProperties.put(
    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
    classOf[IntegerSerializer].getName
  )
  producerProperties.put(
    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
    classOf[StringSerializer].getName
  )

  val producer = new KafkaProducer[Int, String](producerProperties)
  val topic = "movie"
  movieGenerator().foreach { movie =>
    producer.send(new ProducerRecord[Int, String](topic, customPartition(movie.mId), movie.mId, Movie.toCsv(movie)))
  }
  producer.flush()
  producer.close()

  def customPartition(mId: Int): Int = {
    if (mId == 103) 2
    else if (mId == 104) 2
    else 0
  }

  def movieGenerator(): List[Movie] = {
    List(
      Movie(113,"The Sound of Music",1965,"Robert Wise"),
      Movie(114,"E.T.",1982,"Steven Spielberg"),
      Movie(115,"Titanic",1997,"James Cameron")
    )
  }

}

case class Movie(mId: Int, title: String, year: Int, director: String)
object Movie {
  def toCsv(movie: Movie): String =
    s"${movie.mId},${movie.title},${movie.year},${movie.director}"
}



