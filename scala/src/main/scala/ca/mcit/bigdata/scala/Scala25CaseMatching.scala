package ca.mcit.bigdata.scala

object Scala25CaseMatching extends App {

  /*
  GOOD
  if (condition) ...
  else ...
  BAD
  if (condition) {
    if (condition)
      ...
    else
      ...
  } if else(condition)
    ...
  else
    ...
   */

  case class Scala25Person(name: String, age: Int)

  val x = Scala25Person("John", 30)
  println(personMatcher(x))
  println(personMatcher(Scala25Person("John", 31)))
  println(personMatcher(Scala25Person("John", 29)))
  println(personMatcher(Scala25Person("Joe", 40)))

  def personMatcher(person: Scala25Person): String = person match {
    case Scala25Person("John", 30) => "This is John 30 years old"
    case Scala25Person("John", age) if age < 30 => s"This is John $age years old"
    case Scala25Person("Joe", _) => s"This is Joe"
    case _ => "I don't know who is this"
  }

}
