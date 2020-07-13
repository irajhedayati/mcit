package ca.mcit.collection
import scala.collection.immutable

object CollectionPractice extends App {

  val fruits: List[String] = List("apple", "orange")
  val numbers: List[Int] = List(1, 2, 3, 4, 5)
//  println(numbers)

  val p1 = new Person2(1, "John", "Doe")
  val people: List[Person2] = List(p1)

  val numbers2: List[Int] = 1 :: 2 :: 3 :: Nil
//  println(numbers2)

  val x = Nil
//  println(Nil) // List()
//  println(s"Is empty: ${numbers.isEmpty}")
//  println(s"Non-empty: ${numbers.nonEmpty}")
//  println(s"Head: ${numbers.head}")
//  println(s"Tail: ${numbers.tail}")
//  println(s"Init: ${numbers.init}")
//  println(s"Last: ${numbers.last}")

  def exampleFunc(): String = {
    return "John Doe"
  }

//  def sum(input: List[Int]): Int = {
//    if (input.isEmpty) 0
//    else {
//      println(s"Sum of $input = ${input.head} + sum of ${input.tail}")
//      input.head + sum(input.tail)
//    }
//  }

//  println(sum(numbers))

//  def sum(input: List[Int]): Int =
//    if (input.isEmpty) 0
//    else input.head + sum(input.tail)

  def sum(input: List[Int]): Int = input match {
    case Nil => 0
    case x :: xs => x + sum(xs)
  }

  def listMatcher(in: List[Any]): Unit = in match {
    case Nil          => println("It's an empty list")
    case 1 :: Nil     => println("A list with only one element and it is 1")
    case 1 :: 2 :: xs => println("It's a list starting with 1 and 2")
    case x :: Nil     => println("It's a list with single element")
    case _            => println("I don't know what you're talking about")
  }

  listMatcher(Nil)
  listMatcher(List(1))
  listMatcher(List(1, 2))
  listMatcher(List(1, 2, 3))
  listMatcher(List(4))
  listMatcher(List(4, 5, 6))


  (1 to 5).toList.take(2)
  numbers.take(2)
  numbers take 2


//  numbers
//      .filter(x => x % 2 != 0)
//      .map(x => x * 2)
//      .foreach(x => println(x))
}
