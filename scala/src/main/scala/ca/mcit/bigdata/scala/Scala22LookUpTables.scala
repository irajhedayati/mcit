package ca.mcit.bigdata.scala

import scala.collection._

object Scala22LookUpTables extends App {

  /*
  Student               Geo
  ID, Name, City        City, Province
  ====================================
  1,  John, Montreal    Montreal, QC
  2,  Joe,  Toronto     Toronto,  ON
  3,  Iraj, Ottawa

  Number of students in each province?

  John lives in Montreal and Montreal is in QC => 1 student in QC
  Joe lives in Toronto and Toronto is in ON    => 1 student in ON
  Iraj lives in Ottawa and Ottawa is in ON     => 1 student in ON

  - In relational database, it's called JOIN    : JOIN the Student table with the Geo table
  - In data engineering, it's called enrichment : enrich the students with the geo information
  - In general programming, lookup province information of the city that each student lives from geo dataset
    * one of the data structures to implement lookup table is MAP
    * A MAP is a key-value data structure
    * Obviously, value is the record for example "Montreal, QC"
    * The key is how we query the lookup table

  SELECT Province, COUNT(*)
  FROM Student JOIN Geo ON City
  GROUP BY Province
   */

  // 0.a. define schema or data model
  case class Student(id: Int, name: String, city: String)

  case class Geo(city: String, province: String)

  case class EnrichedStudent(student: Student, province: String)

  // 0.b. prepare the source
  val students: List[Student] = List(
    Student(1, "John", "Montreal"),
    Student(2, "Joe", "Toronto"),
    Student(3, "Iraj", "Ottawa"),
    Student(4, "Jenny", "Laval")
  )
  val geos: List[Geo] = List(
    Geo("Montreal", "QC"),
    Geo("Toronto", "ON"),
    Geo("Laval", "QC")
  )

  // 1. create the lookup structure (is a map)
  // -> convert each item in the list to a tuple (key, value) i.e. (city, Geo)
  // val x: List[(String, Geo)] = geos.map(geo => (geo.city, geo))
  // -> use toMap API of the list to convert a list of tuples to a map
  val geoLookup: Map[String, Geo] = geos.map(geo => geo.city -> geo).toMap
  // -> how to lookup information from a MAP
  println(geoLookup("Montreal"))
  println(s"The province of Montreal is ${geoLookup("Montreal").province}")

  // 2. JOIN student with geo (simulating LEFT JOIN)
  val enrichedStudent = students
    .map { student =>
      val province = if (geoLookup.contains(student.city)) geoLookup(student.city).province else null
      EnrichedStudent(student, province)
    }
  println("=========")
  enrichedStudent.foreach(println)

  // 3. Make it INNER JOIN
  //  val enrichedStudentInnerJoin = enrichedStudent.filter(x => x.province != null)
  //  val enrichedStudentInnerJoin = enrichedStudent.filter(_.province != null)
  val enrichedStudentInnerJoin = enrichedStudent.filterNot(_.province == null)
  println("=========")
  enrichedStudentInnerJoin.foreach(println)

  // 4. count students in each province
  //  val provinceStudentCount =
  //    enrichedStudentInnerJoin
  //      .groupBy(enrichedStudent => enrichedStudent.province)
  //  **** the output of groupBy is a map [province -> enriched student]
  //  QC -> John, Jenny  =>  QC -> 2
  //  ON -> Joe          =>  ON -> 1
  //      .map(provinceStudentList => provinceStudentList._1 -> provinceStudentList._2.size)
  val provinceStudentCount = enrichedStudentInnerJoin.groupBy(_.province).mapValues(_.size)

  println("=========")
  provinceStudentCount.foreach(println)
}
