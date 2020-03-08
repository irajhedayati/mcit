package ca.mcit.bigdata.function

/**
 * A higher-order function is a function that takes another functions as parameters.
 */
object HigherOrderFunction {

  /**
   * Runs the `action` on number 10 and returns a string.
   * It makes it a higher-order function.
   *
   * @param action a function that accepts an integer and returns an integer
   * @return the result of `action` on 10
   */
  def processInteger(action: Int => Int): String = action(10).toString

  /*
  Here we first pass `addOne` function to `processInteger` function. It passes number 10 to the function that returns
  number 11. Then it will cast the return value of 11 to string.
  The string "11" which is the result of `processInteger` will be passed to `println` function and will print it on
  the console.
   */
  println(processInteger(FunctionPractice.addOne))

  /*
  processInteger(FunctionPractice.addOne) = println( FunctionPractice.addOne(10).toString )
   */


}
