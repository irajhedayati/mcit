package ca.mcit.bigdata.hive

import java.sql.{Connection, DriverManager}

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
  // we use in beeline to connect to the Hive server
  val connection: Connection = DriverManager.getConnection("jdbc:hive2://172.16.129.58:10000")
  val stmt = connection.createStatement()

  // Step 3: run the query and process the results
  val res = stmt.executeQuery("SHOW TABLES IN iraj")
  while (res.next()) {
    println(res.getString(1))
  }

  // Step 4: close resources
  stmt.close()
  connection.close()
}
