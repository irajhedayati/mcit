package ca.mcit.bigdata.scala.function

object HigherOrderFunction2 {

  /**
   * A good example of higher-order function is the set of functions on collections such as:
   * - map: accepts a function that has an input and an output. It will be applied on each member of the collection
   *   resulting a new collection
   * - filter: accepts a function that has an input and the output is either `true` or `false`. It applies on each
   *   member of collection and results a new collection including those that the output is `true`
   * - foreach: accepts a function that has an input and the output is `Unit`. It will have no output and just a
   *   side effect
   */
  (1 to 10).map(FunctionPractice.addOne)

  def isOdd(in: Int): Boolean = ???
  (1 to 10).map(FunctionPractice.addOne).filter(isOdd)


  (1 to 10).map(FunctionPractice.addOne).filter(isOdd).foreach(println)

}
