package ca.mcit.bigdata.hive

import org.apache.hive.jdbc.HiveDriver

import java.sql.DriverManager

object Hive03Execute extends App {

  val driverName: String = classOf[HiveDriver].getName
  Class.forName(driverName)

  val connectionString: String = "jdbc:hive2://quickstart.cloudera:10000/bdsf2001_iraj;user=cloudera"

  val connection = DriverManager.getConnection(connectionString)
  val stmt = connection.createStatement()

  val hasResultSet = stmt.execute(
    """INSERT OVERWRITE TABLE enriched_rating
      |SELECT m.id, title, rid, stars, ratingdate
      |FROM ext_movie m JOIN ext_rating r ON m.id = r.mid""".stripMargin)

  if (hasResultSet) println("I received a result set")
  else println("It was an update query and I haven't received a result set")

  val hasResultSet2 = stmt.execute("select * from ext_movie_wo_header_w_null")
  if (hasResultSet2) {
    val rs = stmt.getResultSet
    while (rs.next()) {
      println(rs.getInt(1))
    }
    rs.close()
  }

  stmt.close()
  connection.close()

}
