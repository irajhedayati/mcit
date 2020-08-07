package ca.mcit.bigdata.hive

import java.sql.DriverManager
import java.util.UUID

import org.apache.hadoop.fs.Path

object MovieEnricher extends HdfsClient {

  val staging = "/user/fall2019/iraj"

  /* TRANSFORMATION */
  val movieStream = fs.open(new Path(s"$staging/movie/movie.csv"))
  val movieLookup: Map[Int, Movie] = Iterator.continually(movieStream.readLine()).takeWhile(_ != null)
    .toList
    .tail
    .map(_.split(",", -1))
    .map(p => {
      val mid = p(0).toInt
      mid -> Movie(mid, p(1), p(2).toInt, p(3))
    })
      .toMap
  movieStream.close()

  val ratingStream = fs.open(new Path(s"$staging/rating/rating.csv"))
  val ratings: Seq[Rating] = Iterator.continually(ratingStream.readLine()).takeWhile(_ != null)
    .toList
    .tail
    .map(_.split(",", -1))
    .map(p => Rating(p(0).toInt, p(1).toInt, p(2).toInt, p(3)))
  ratingStream.close()

  val enrichedMovie: Seq[EnrichedMovie] = ratings
    .map(rating => EnrichedMovie(movieLookup(rating.mid), rating))

  /* LOAD */
  // 1. Where to load:
  //  Hive DWH table 'enriched_movie_new' which is partitioned by year
  // 2. Encoding
  //  CSV without header
  val dbName = "iraj"
  val tableName = "enriched_movie"
  val tablePath = s"/user/hive/warehouse/$dbName.db/$tableName"
  enrichedMovie
    .groupBy(_.movie.year)
    .foreach {
      case (year, enrichedMovies) =>
        /** Check if the partition directory exists; if not create it*/
        val partitionPath = new Path(s"$tablePath/year=$year")
        if (!fs.exists(partitionPath)) {
          fs.mkdirs(partitionPath)
        }
        val outPath = new Path(s"$tablePath/year=$year/${UUID.randomUUID().toString}.csv")
        val outputStream = fs.create(outPath)
        enrichedMovies.foreach(enrichedMovie => {
          outputStream.writeUTF(EnrichedMovie.toCsv(enrichedMovie))
          outputStream.writeUTF("\n")
        })
        outputStream.close()
    }

  fs.close()

  val driverName: String = "org.apache.hive.jdbc.HiveDriver"
  Class.forName(driverName)
  val connectionString: String = "jdbc:hive2://quickstart.cloudera:10000/iraj;user=cloudera;"
  val connection = DriverManager.getConnection(connectionString)
  val stmt = connection.createStatement()
  stmt.execute("MSCK REPAIR TABLE iraj.enriched_movie")
  stmt.close()
  connection.close()
}

case class Movie(mid: Int, title: String, year: Int, director: String)
object Movie {
  def toCsv(movie: Movie): String =
    s"${movie.mid},${movie.title}"
}

case class Rating(rid: Int, mid: Int, stars: Int, ratingdate: String)
object Rating {
  def toCsv(rating: Rating): String =
    s"${rating.rid},${rating.stars},${rating.ratingdate}"
}

case class EnrichedMovie(movie: Movie, rating: Rating)
object EnrichedMovie {
  def toCsv(enrichedMovie: EnrichedMovie): String =
    s"${Movie.toCsv(enrichedMovie.movie)},${Rating.toCsv(enrichedMovie.rating)}"
}
