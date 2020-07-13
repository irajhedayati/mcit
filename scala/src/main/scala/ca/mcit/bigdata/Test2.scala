package ca.mcit.bigdata

import scala.collection.immutable

object Test2 extends App {

  val numbers: List[Int] = (1 to 10).toList
  val oddNumbers = numbers.filterNot(in => in % 2 != 0)
                                        // Int ......... Int  ................... String
  val numbersBy2: List[String] = oddNumbers.map(in => in * 2).map(in => in.toString)

  println(numbers)
  println(oddNumbers)
  println(numbersBy2)

  numbersBy2.foreach(foreachFunction)

//  def multiplyBy2(in: Int): Int = in * 2
   def foreachFunction(in: String): Unit = {
     println(in)
   }
}
