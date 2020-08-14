package ca.dataedu.spark.core.model

case class Rating(rID: Int, mID: Int, stars: Int, ratingDate: Option[String])
object Rating {

  /**
    * 201,101,2,2011-01-22
    * 202,106,4,
    */
  def apply(csv: String): Rating = {
    val fields = csv.split(",", -1)
    val ratingDate = if (fields(3).isEmpty) None else Option(fields(3))
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toInt, ratingDate)
  }

  def toCsv(rating: Rating, movie: Movie): String =
    s"${rating.rID},${rating.stars},${movie.title}"

}
