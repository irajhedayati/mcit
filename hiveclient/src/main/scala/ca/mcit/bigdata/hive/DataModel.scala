package ca.mcit.bigdata.hive

case class Movie(mId: Int, title: String, year: Int, director: String)

object Movie {
  def apply(csv: String): Movie = {
    val fields = csv.split(",", -1)
    Movie(fields(0).toInt, fields(1), fields(2).toInt, fields(3))
  }
  def toCsv(movie: Option[Movie]): String = movie match {
    case Some(x) => s"${x.mId},${x.title}"
    case None => ","
  }
}

case class Rating(rId: Int, mId: Int, stars: Int, ratingDate: Option[String])

object Rating {
  def apply(csv: String): Rating = {
    val fields = csv.split(",", -1)
    val date = if (fields(3).nonEmpty) Option(fields(3)) else None
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toInt, date)
  }
  def toCsv(rating: Rating): String =
    s"${rating.rId},${rating.stars},${rating.ratingDate.getOrElse("")}"
}

case class EnrichedRating(rating: Rating, movie: Option[Movie])

object EnrichedRating {
  def toCsv(enrichedRating: EnrichedRating): String = {
    s"${Movie.toCsv(enrichedRating.movie)},${Rating.toCsv(enrichedRating.rating)}"
  }
}
