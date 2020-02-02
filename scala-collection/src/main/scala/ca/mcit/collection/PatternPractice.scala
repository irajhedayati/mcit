package ca.mcit.collection

object PatternPractice extends App {

  val x = "A"
  x match {
    case "A" => println("Matched")
    case "B" => println("Not matched")
  }
  val john = Person("John", 30)
  john match {
    case Person("John", 30) => println("I found the exact match")
    case Person("John", _) => println("I found a John")
    case Person(name, age) if age < 30 => println(s"I found $name less than 30")
  }

}

case class Person(name: String, age: Int)