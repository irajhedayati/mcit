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
  // Step 1: load the Hive JDBC driver
  val driverName: String = "org.apache.hive.jdbc.HiveDriver"
  Class.forName(driverName)

  // Step 2: connect to the server
  // A connection requires a connection string. This is equal to what
  // we use in beeline to connect to the Hive server.
  /*
  jdbc:hive2://<host>:<port>/<dbName>;<sessionConfs>?<hiveConfs>#<hiveVars>
  - host = 172.16.129.58
  - port = 10000 (default Hive server port)
  - dbName = iraj
  - sessionConfs (semicolon separated)
    - user = cloudera
    - password = cloudera
   */
  val connection: Connection = DriverManager.getConnection("jdbc:hive2://172.16.129.58:10000/iraj;user=cloudera;password=cloudera")
  val stmt = connection.createStatement()

  // Step 3: run the query and process the results
  stmt.execute("SET hive.exec.dynamic.partition.mode=nonstrict")
  stmt.execute("DROP TABLE IF EXISTS enriched_movie_p")
  stmt.execute("CREATE TABLE enriched_movie_p ( " +
    " mid INT, " +
    " title STRING, " +
    " rid INT, " +
    " stars INT, " +
    " ratingdate STRING) " +
    " PARTITIONED BY (year INT) " +
    " ROW FORMAT DELIMITED " +
    " FIELDS TERMINATED BY ',' " +
    " STORED AS TEXTFILE")
  stmt.executeQuery("INSERT OVERWRITE TABLE enriched_movie_p PARTITION(year) "+
    " SELECT mid, title, rid, stars, ratingdate, year "+
    " FROM enriched_movie")
  val res: ResultSet = stmt.executeQuery("SELECT * FROM enriched_movie")
  while (res.next()) {
    println("MID: " + res.getInt(2))
  }

  // Step 4: close resources
  stmt.close()
  connection.close()
}
