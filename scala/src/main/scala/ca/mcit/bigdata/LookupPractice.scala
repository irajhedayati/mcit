package ca.mcit.bigdata

case class Student(id: Int, name: String, city: String)
case class Geo(city: String, province: String)
case class EnrichedStudent(student: Student, province: String)

object LookupPractice extends App {

  /*
  Student               Geo
  ID, Name, City        City, Province
  ====================================
  1,  John, Montreal    Montreal, QC
  2,  Joe,  Toronto     Toronto,  ON
  3,  Iraj, Ottawa
  Number of students in each province?

  SELECT Province, COUNT(*) FROM Student JOIN Geo ON City GROUP BY Province
   */

  val students = List(
    Student(1, "John", "Montreal"),
    Student(2, "Joe", "Toronto"),
    Student(3, "Iraj", "Ottawa")
  )
  val geos = List(
    Geo("Montreal", "QC"),
    Geo("Toronto", "ON")
  )

  // 1. Create lookup table of Geo
  val geoLookup: Map[String, Geo] = geos.map(g => g.city -> g).toMap

//  geoLookup.get("Ottawa").map(_.province).foreach(println)

  // 2. Join students list
  students
    .map(student => {
      val studentGeo: Option[Geo] = geoLookup.get(student.city)
      val studentProvince: Option[String] = studentGeo.map(_.province)
      EnrichedStudent(student, studentProvince.orNull)
    })
    .filter(_.province != null)
    .filterNot(_.province == null)
    .filter(enrichedStudent => Option(enrichedStudent.province).isDefined)
    .foreach(println)

//  val enrichedStudents =
//    for {student <- students; g <- geo; if student.city == g.city} yield EnrichedStudent(student, g.province)
//
//  enrichedStudents foreach println

}
