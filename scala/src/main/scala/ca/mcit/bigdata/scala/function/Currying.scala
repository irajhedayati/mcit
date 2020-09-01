package ca.mcit.bigdata.scala.function

/**
 * Currying is the technique of transforming a function with multiple arguments into a function with fewer argument
 */
object Currying extends App {

  /** A function with two arguments */
  def add(x: Int, y: Int): Int = ???

  println(add(3,5))

  /** If most of the time the second argument is number 5, then, it will be hard to maintain this code.
   * We can just change the signature. It still accepts two arguments adn returns the sum.
   *  */
  def add(x: Int)(y: Int): Int = ???

  /** Here we have this option to define a new function where sets one of the arguments to 5 */
  def addTo5(y: Int): Int => Int = add(5)

  println(addTo5(3))
}
