package ca.mcit.bigdata.hive

import java.sql.{Connection, DriverManager, ResultSet}

/**
 * In this class, we try to connect to a Hive server using JDBC connection.
 * This enables us to query Hive in our application.
 *
 * The process is quite similar to any other JDBC driver.
 * 1. Load the driver into the JVM. It should be available in the classpath
 * 2. Create a connection
 * 3. Run queries
 * 4. Close the connection
 */
object HiveClient extends App {

  val driverName: String = "org.apache.hive.jdbc.HiveDriver"
  Class.forName(driverName)
  val connectionString: String = "jdbc:hive2://quickstart.cloudera:10000/iraj;user=cloudera;"
  val connection = DriverManager.getConnection(connectionString)
  val stmt = connection.createStatement()

//  stmt.execute("DROP TABLE iraj.ext_movie")
//  // run non-SELECT queries
//  stmt.executeUpdate("DROP TABLE iraj.ext_movie")

  stmt.execute(
//  stmt.executeUpdate(
    """CREATE TABLE iraj.enriched_movie_p (
      | mid INT,
      | title STRING,
      | rid INT,
      | stars INT,
      | ratingdate STRING
      |) STORED AS parquet""".stripMargin
  )
  stmt.executeUpdate("""INSERT OVERWRITE TABLE iraj.enriched_movie_p
                       |SELECT m.mid, title, rid, stars, ratingdate
                       |FROM iraj.ext_movie m JOIN iraj.ext_rating r ON m.mid = r.mid""".stripMargin)

  // run SELECT queries
  stmt.executeQuery("SELECT * FROM iraj.ext_movie")

//  val rs = stmt.executeQuery(
//    """SELECT title, avg(stars)
//      |FROM iraj.enriched_movie_p
//      |GROUP BY title""".stripMargin)
  val hasResultSet: Boolean = stmt.execute(
    """
      |SELECT title, avg(stars)
      |FROM iraj.enriched_movie_p
      |GROUP BY title""".stripMargin)

  if (hasResultSet) {
    val rs = stmt.getResultSet
    while(rs.next()) {
      println(s"Title: ${rs.getString(1)} \t Avg. Stars: ${rs.getFloat(2)}")
    }
  }

  stmt.close()
  connection.close()
}
