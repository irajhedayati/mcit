package ca.mcit.bigdata.scala

/**
  * Function is the first-class citizen in Scala
  * Function is an expression that takes parameters
  *
  * def functionName(param1, param2, ...., paramn): OutputType = {
  * // do something
  * }
  */
object Scala15Function extends App {

  /**
    * accepts an integer and returns it incremented by one
    */
  def addOne(in: Int): Int = in + 1

  println(addOne(5))

  /*
   * accepts an integer and returns the original value along with the incremented value
   */
  def addOneWithOriginal(in: Int): (Int, Int) = (in, in + 1)

  // use it as tuple
  val tuple = addOneWithOriginal(5)
  println(s"The original value was ${tuple._1}")
  println(s"The incremented value is ${tuple._2}")

  // use it as variables
  val (original, incremented) = addOneWithOriginal(5)
  println(s"The original value was $original")
  println(s"The incremented value is $incremented")

}
