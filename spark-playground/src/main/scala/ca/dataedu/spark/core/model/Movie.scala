package ca.dataedu.spark.core.model

case class Movie(mId: Int, title: String, year: Int, director: Option[String])
object Movie {
  def fromCsv(movie: String): Movie = {
    val fields = movie.split(",", -1)
    Movie(fields(0).toInt, fields(1), fields(2).toInt, if (fields.length == 4) Option(fields(3)) else None)
  }
}
