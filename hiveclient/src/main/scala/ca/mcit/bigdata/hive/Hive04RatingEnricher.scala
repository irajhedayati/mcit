package ca.mcit.bigdata.hive

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import java.io.{BufferedReader, InputStreamReader}
import java.util.UUID

/**
  * In this tutorial, we populate data of a partitioned table.
  *
  * Before we start, create a table
  *
  * {{{
  *   CREATE TABLE `enriched_rating_csv_p`(
  *	    `mid` int,
  *	    `title` string,
  *	    `rid` int,
  *	    `stars` int,
  *	    `rating_date` string
  *	  )
  *	  ROW FORMAT DELIMITED
  *	  FIELDS TERMINATED BY ','
  *	  STORED AS TEXTFILE
  *	  TBLPROPERTIES ( 'serialization.null.format' = '' );
  * }}}
  *
  * The code will first read `movie` and `rating` files from the stating area and calculates an inner join using
  * in-memory lookup. Then it will write the result on HDFS under appropriate directory.
  */
object Hive04RatingEnricher extends App {

  private val conf = new Configuration() // loads with default values
  val hadoopConfDir = System.getenv("HADOOP_CONF_DIR")
  conf.addResource(new Path(s"$hadoopConfDir/core-site.xml"))
  conf.addResource(new Path(s"$hadoopConfDir/hdfs-site.xml"))
  val fs: FileSystem = FileSystem.get(conf)

  val staging = "/user/bdsf2001/iraj"

  val movie = new BufferedReader(new InputStreamReader(fs.open(new Path(s"$staging/movie/movie.csv"))))
  val movieLookup: Map[Int, Movie] =
    Iterator.continually(movie.readLine()).takeWhile(_ != null).toList
      .tail // remove the header
      .map(Movie(_)) // convert to Movie object (JVM object)
      .map(movie => movie.mId -> movie).toMap // create a lookup map

  val ratingSource =
    new BufferedReader(new InputStreamReader(fs.open(new Path(s"$staging/rating/rating.csv"))))
  val ratings: List[Rating] =
    Iterator.continually(ratingSource.readLine()).takeWhile(_ != null).toList
      .tail // remove the header
      .map(Rating(_)) // convert to Movie object (JVM object)

  val enrichedRating = ratings.map(rating => EnrichedRating(rating, movieLookup.get(rating.mId)))

  val outPath = new Path(
    s"/user/hive/warehouse/bdsf2001_iraj.db/enriched_rating_csv/${UUID.randomUUID().toString}"
  )
  val outputStream = fs.create(outPath)
  enrichedRating.foreach { record =>
    outputStream.write(EnrichedRating.toCsv(record).getBytes)
    outputStream.write("\n".getBytes)
  }
  outputStream.close()

  fs.close()

}
