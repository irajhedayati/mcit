package ca.mcit.bigdata.scala

import scala.collection.immutable

object Scala10List extends App {

  val fruits: List[String] = List("Apple", "Orange")
  val numbers: List[Int] = List(1, 2, 3)

  println(fruits)
  println(numbers)

  // Collections are immutable
  // numbers1 = (1,2,3)
  // numbers2 = numbers1 + 4
  val numbers2 = numbers ++ List(4)

  // you can have list of lists
  /*
    - List(1,2,3)
    - List(4,5,6)
    like a matrix
    1 2 3
    4 5 6
   */
  val nestedCollection =
  List(
    List(1, 2, 3),
    List(4, 5, 6)
  )

  val emptyList = List()

  // "cons" operation in the lists
  // number + number
  // item :: List
  val singleItemList = 4 :: List()
  val singleItemList2 = 4 :: Nil
  println(singleItemList)

  // Frequently used API of List
  // numbers = List(1, 2, 3)
  val head = numbers.head

  println(s"Head of the list is $head")
  println(s"Last item in the list is ${numbers.last}")
  println(s"The tail of the list is ${numbers.tail}")
  println(s"The initial part of the list is ${numbers.init}")
  println(s"Is the list empty? ${numbers.isEmpty}")
  println(s"Is the list non-empty? ${numbers.nonEmpty}")

  def myReverse(in: List[Int]): List[Int] = in match {
    case Nil => Nil
    case x :: xs => myReverse(xs) ++ List(x)
  }

  println(s"Reverse of List(1, 2, 3) is ${myReverse(numbers)}")

  println(s"The second item in the list is ${numbers(1)}")
  println(s"The number of items in the list is ${numbers.size}")

  // Simple pattern matching to work with the lists
  /**
    * 1. if it is empty; print "It's an empty list"
    * 2. If not empty, check if the first item is 1; if yes, print "A list with only one element and it is 1"
    * 3. If it is a list starting with 1 and 2; if yes, print "It's a list starting with 1 and 2"
    * 4. If it is a list with single element; "It's a list with single element"
    * 5. otherwise, print "I don't know what you're talking about"
    */
  def listProcessor(in: List[Int]): Unit = {
    if (in.isEmpty) {
      println("It's an empty list")
    } else if (in == List(1)) {
      println("A list with only one element and it is 1")
    } else if (in.size >= 2 && in.head == 1 && in.tail.head == 2) {
      println("It's a list starting with 1 and 2")
    } else if (in.size == 1) {
      println("It's a list with single element")
    } else {
      println("I don't know what you're talking about")
    }
  }

  listProcessor(Nil)
  listProcessor(List(1))
  listProcessor(List(1, 2))
  listProcessor(List(1, 2, 3))
  listProcessor(List(4))
  listProcessor(List(4, 5, 6))

  /**
    * Same implementation as listProcessor but using pattern matching
    */
  def listMatcher(in: List[Int]): Unit = in match {
    case Nil => println("It's an empty list")
    case List(1) => println("A list with only one element and it is 1")
    //    case 1 :: Nil => println("A list with only one element and it is 1")
    case 1 :: 2 :: xs => println("It's a list starting with 1 and 2")
    case x :: Nil => println("It's a list with single element")
    case _ => println("I don't know what you're talking about")
  }

  listMatcher(Nil)
  listMatcher(List(1))
  listMatcher(List(1, 2))
  listMatcher(List(1, 2, 3))
  listMatcher(List(4))
  listMatcher(List(4, 5, 6))

  // more on the collection APIs
  println(s"The first 2 elements of List(1, 2, 3, 4) are ${List(1, 2, 3, 4).take(2)}")
  println(s"The list of List(1, 2, 3, 4) without first 2 elements is ${List(1, 2, 3, 4).drop(2)}")

  // converting collections
  val numbersAsList: List[Int] = List(1, 2, 3, 4, 5)
  val numbersAsSeq: immutable.Seq[Int] = numbersAsList.toSeq
  val numbersAsArray = numbersAsList.toArray

  // using range
  val range = 1 to 10
  println(range)
  println(range.toList)
  println((1 until 10).toList)
}
