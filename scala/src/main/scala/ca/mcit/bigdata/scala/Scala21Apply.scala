package ca.mcit.bigdata.scala

object Scala21Apply extends App {

  // Instantiate an instance of "person" with default constructor passing
  // all the parameters
  val john = PersonWithApply("John", 25)

  /*
  In order to parse a CSV, extra work is needed.
  We implement a parser function to simplify it
   */
  val joeAsCsv = "Joe,30"
  val joe = personFromCsv(joeAsCsv)
  def personFromCsv(line: String): PersonWithApply = {
    val fields = line.split(",", -1)
    PersonWithApply(fields(0), fields(1).toInt)
  }

  /**
    * Having a parser function is a practice that we do most of the time to
    * either instantiate an object based on external data e.g. CSV, JSON, and etc.
    * or from an internal data model to another internal data model
    * (i.e. create an instance of a case class based on instances of one or
    * more other case classes)
    *
    * Using "apply" function:
    * 1. add a companion object
    * 2. add 'apply' function: def apply(PARAMETERS): Class = ???
    */
  val joeFromApply = PersonWithApply(joeAsCsv)
  val lines: List[String] = List(
    "Joe,30",
    "John,25"
  )
  lines.map(line => PersonWithApply(line))

}

case class PersonWithApply(name: String, age: Int)
object PersonWithApply {
  def apply(csv: String): PersonWithApply = {
    val fields = csv.split(",", -1)
    PersonWithApply(fields(0), fields(1).toInt)
  }
}
