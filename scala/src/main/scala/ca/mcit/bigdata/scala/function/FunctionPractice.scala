package ca.mcit.bigdata.scala.function

/**
 * Function is the first-class citizen in Scala
 * Function is an expression that takes parameters
 *
 * ```
 * def functionName(param1, param2, ...., paramN): OutputType = {
 *    // do something
 * }
 * ```
 */
object FunctionPractice extends App {

  /**
   * accepts an integer and returns it incremented by 1
   *
   * @param in input integer value
   * @return `in` incremented by 1
   */
  def addOne(in: Int): Int = ???

  println(addOne(5))
}
