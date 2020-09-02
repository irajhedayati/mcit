package ca.mcit.bigdata.scala

object Scala5String extends App {

  val name = "John"
  val age = 30
  println("My name is" + name + "and I'm " + age + "years old")

  // String interpolation
  println(s"My name is $name and I'm $age years old")

  val sqlQuery = "SELECT name, age FROM person WHERE name LIKE '%John%' AND age >= 40 AND age <= 30 AND city = 'Montreal'"
  println(sqlQuery)
  val sqlQuery2 =
    "SELECT name, age \n" +
      "FROM person \n" +
      "WHERE name LIKE '%John%' AND age >= 40 AND age <= 30 AND city = 'Montreal'"

  println(sqlQuery2)

  val sqlQuery3 =
    """SELECT name, age
      |FROM person
      |WHERE name LIKE '%<John>%' AND age >= 40 AND age <= 30 AND city = 'Montreal'""".stripMargin
  println(sqlQuery3)

  val sqlQuery4 =
    s"""SELECT name, age
       |FROM person
       |WHERE name LIKE '%$name%' AND age >= 40 AND age <= 30 AND city = 'Montreal'""".stripMargin
  println(sqlQuery4)

  val sqlQuery5 =
    s"""SELECT name, age
       %FROM person
       %WHERE name LIKE '%$name%' AND age >= 40 AND age <= 30 AND city = 'Montreal'""".stripMargin('%')
  println(sqlQuery5)

  /*
  {
    "name": "John",
    "age": 30
  }
   */

  val json = "{\"name\": \"John\", \"age\": 30}"
  val json1 = "{" +
    "   \"name\": \"John\", " +
    "   \"age\": 30" +"" +
    "}"
  val json2 =
    """{
      |   "name": "John",
      |   "age": 30
      |}""".stripMargin

}
