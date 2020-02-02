package ca.mcit.collection

object CollectionPractice extends App {

//  def sum(input: List[Int]): Int = {
//    if (input.isEmpty) 0
//    else {
//      println(s"Sum of $input = ${input.head} + sum of ${input.tail}")
//      input.head + sum(input.tail)
//    }
//  }

//  def sum(input: List[Int]): Int =
//    if (input.isEmpty) 0
//    else input.head + sum(input.tail)

  def sum(input: List[Int]): Int = input match {
    case Nil => 0
    case x :: xs => x + sum(xs)
  }


  val numbers = List(1,2,3)
  numbers.take(2)
  numbers take 2

  //println(sum(numbers))
//  println(Nil) // List()
//  println(numbers)
//  println(s"Is empty: ${numbers.isEmpty}")
//  println(s"Non-empty: ${numbers.nonEmpty}")
//  println(s"Head: ${numbers.head}")
//  println(s"Tail: ${numbers.tail}")
//  println(s"Init: ${numbers.init}")
//  println(s"Last: ${numbers.last}")
  numbers
      .filter(x => x % 2 != 0)
      .map(x => x * 2)
      .foreach(x => println(x))
}
