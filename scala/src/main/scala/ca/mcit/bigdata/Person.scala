package ca.mcit.bigdata

case class Person(name: String, age: Int)

object Person {
  def apply(csvLine: String): Person = {
    val p = csvLine.split(",")
    new Person(p(0), p(1).toInt)
  }
}
