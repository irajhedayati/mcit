package ca.mcit.bigdata.scala

object Scala6Expression extends App {

  val x = 5
  val y = 7
  // expression is a piece of code that returns something
  // even though you don't use it
  val z: Int = x + y
  x + y

  // statement is a piece of code that doesn't return anything
  // we basically use its side effect
  println(x + y)
  println(z)

  // when a piece of code doesn't return anything, it is Unit
  def printSum(number1: Int, number2: Int): Unit = println(number1 + number2)

  println(s"The sum of $x and $y is ${x + y}")
  // Expression 1: x + y which returns 12
  // Expression 2: s"The sum of $x and $y is ${x + y}" which returns "The sum of 5 and 7 is 12"
  // Statement: println("The sum of 5 and 7 is 12") which prints "The sum of 5 and 7 is 12" to the console and
  //            returns nothing (Unit)

}
