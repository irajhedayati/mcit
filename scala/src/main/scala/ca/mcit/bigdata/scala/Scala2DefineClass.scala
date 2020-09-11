package ca.mcit.bigdata.scala

import ca.mcit.bigdata.scala.schema.Person

object HelloWorld2 extends App {

  println("Hello world!")

  val p1 = new Person("John", 35)

}

class Person(name: String, age: Int)
