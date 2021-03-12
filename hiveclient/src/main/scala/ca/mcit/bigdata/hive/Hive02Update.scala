package ca.mcit.bigdata.hive

import ca.mcit.bigdata.hive.Hive01SimpleSelect.{connection, stmt}
import .stmt
import org.apache.hive.jdbc.HiveDriver

import java.sql.DriverManager

object Hive02Update extends App {

  val driverName: String = classOf[HiveDriver].getName
  Class.forName(driverName)

  val connectionString: String = "jdbc:hive2://quickstart.cloudera:10000/bdsf2001_iraj;user=cloudera"

  val connection = DriverManager.getConnection(connectionString)
  val stmt = connection.createStatement()

  stmt.executeUpdate(
  """CREATE TABLE enriched_rating (
    | mid INT,
    | title STRING
    | rid INT,
    | stars INT,
    | ratingdate STRING
    |) STORED AS parquet""".stripMargin
  )
  stmt.executeUpdate("""INSERT OVERWRITE TABLE enriched_movie_p
                       |SELECT m.mid, title, rid, stars, ratingdate
                       |FROM ext_movie m JOIN ext_rating r ON m.mid = r.mid""".stripMargin)

  stmt.close()
  connection.close()

}
