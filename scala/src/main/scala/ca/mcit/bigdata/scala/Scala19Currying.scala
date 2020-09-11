package ca.mcit.bigdata.scala

/** A method of transforming of functions */
object Scala19Currying extends App {

  /** A function with two arguments */
  def add(x: Int, y: Int): Int = x + y

  println(add(3, 5))

  /* What if most of the cases the second argument is 5 */

  def curryingAdd(x: Int)(y: Int): Int = x + y

  println(curryingAdd(3)(5))

  def addTo5: Int => Int = curryingAdd(5)

  println(addTo5(3))
}
