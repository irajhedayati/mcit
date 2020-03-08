package ca.mcit.bigdata.function

import ca.mcit.bigdata.function.HigherOrderFunction2.isOdd

/**
 * An anonymous function is a function that doesn't have a name.
 *
 */
object AnonymousFunction extends App {

  /** [[HigherOrderFunction.processInteger()]] is a higher order function that accepts a function that
   * accepts and integer and returns an integer.
   * */
  println(
    HigherOrderFunction.processInteger(
      FunctionPractice.addOne
    )
  )

  /**
   * If it is a one-time use, it would be easier to just define a function on the fly and not name it.
   * `(x: Int) => x + 1` is a function that accepts an integer and returns another integer as a result.
   */
  println(
    HigherOrderFunction.processInteger(
      (x: Int) => x + 1
    )
  )

  /**
   * One the common use cased for the anonymous functions is processing collections
   */
  (1 to 10).map((x: Int) => x + 1).filter(_ % 2 == 1).foreach(println)
  (1 to 10).map(x => x + 1)       .filter(_ % 2 == 1).foreach(println)
  (1 to 10).map(_ + 1)            .filter(_ % 2 == 1).foreach(println)

}
