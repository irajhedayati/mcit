package ca.mcit.bigdata.scala

object Scala23UseOtherClasses extends App {

  println(ObjectWithFunction.myFunction("Montreal"))

  val obj = new ClassWithFunction()
  println(obj.myFunction("Montreal"))

  val obj1 = CaseClassWithFunction("Montreal")
  println(obj1.myFunction())

}

object ObjectWithFunction {
  private val lookupTable = Map(
    ("Montreal", "QC"),
    ("Toronto", "ON"),
    ("Laval", "QC")
  )
  def myFunction(city: String): String = lookupTable(city)
}

class ClassWithFunction() {
  private val lookupTable = Map(
    ("Montreal", "QC"),
    ("Toronto", "ON"),
    ("Laval", "QC")
  )
  def myFunction(city: String): String = lookupTable(city)
}

case class CaseClassWithFunction(city: String) {
  private val lookupTable = Map(
    ("Montreal", "QC"),
    ("Toronto", "ON"),
    ("Laval", "QC")
  )
  def myFunction(): String = lookupTable(city)
}
