package ca.mcit.bigdata.hive

import org.apache.hive.jdbc.HiveDriver

import java.sql.{DriverManager, ResultSet}

object Hive01SimpleSelect extends App {

  val driverName: String = classOf[HiveDriver].getName
  Class.forName(driverName)

  val connectionString: String = "jdbc:hive2://quickstart.cloudera:10000/bdsf2001_iraj;user=cloudera"

  val connection = DriverManager.getConnection(connectionString)
  val stmt = connection.createStatement()

  // Business Logic
  val rs: ResultSet = stmt.executeQuery("select * from ext_movie_wo_header_w_null")
  while(rs.next()) {
    // What you want to do with each row
    println(rs.getInt(1))
  }
  rs.close()

  stmt.close()
  connection.close()

}
