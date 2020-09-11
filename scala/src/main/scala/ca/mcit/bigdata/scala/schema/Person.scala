package ca.mcit.bigdata.scala.schema

case class Person(id: Int, fullName: String)

object Person {

  /**  2,Quintin,Queen,"123 6th St. Melbourne, FL 32904" */
  def apply(csvLine: String): Person = {
    val p: Array[String] = csvLine.split(",")
    Person(p(0).toInt, s"${p(1)} ${p(2)}")
  }

}
