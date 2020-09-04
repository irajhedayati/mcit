package ca.mcit.bigdata.scala

object Scala11Map extends App {

  val romanNumber = Map("I" -> 1, "II" -> 2, "V" -> 5)

  // this returns 1
  println(romanNumber("I"))
  // this fails as "III" doesn't exist
  println(romanNumber("III"))

  // collection API is still valid
  romanNumber.nonEmpty
  romanNumber.isEmpty
  romanNumber.head
  romanNumber.init
  romanNumber.tail
  romanNumber.last

}
