package ca.mcit.bigdata.hive

import org.apache.hive.jdbc.HiveDriver

import java.sql.DriverManager

object Hive02Update extends App {

  val driverName: String = classOf[HiveDriver].getName
  Class.forName(driverName)

  val connectionString: String = "jdbc:hive2://quickstart.cloudera:10000/bdsf2001_iraj;user=cloudera"

  val connection = DriverManager.getConnection(connectionString)
  val stmt = connection.createStatement()

  stmt.executeUpdate(
    """CREATE TABLE IF NOT EXISTS enriched_rating (
      |   mid int,
      |   title string,
      |   rid int,
      |   stars int,
      |   ratingdate string
      |)
      |stored as parquet""".stripMargin)

  stmt.executeUpdate(
    """INSERT INTO TABLE enriched_rating
      |SELECT m.id, title, rid, stars, ratingdate
      |FROM ext_movie m JOIN ext_rating r ON m.id = r.mid""".stripMargin)

  stmt.close()
  connection.close()
}
