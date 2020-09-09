package ca.mcit.bigdata.scala

/**
  * A higher order function is a function that accepts another function
  */
object Scala17HigherOrderFunction extends App {

  /**
    * Accepts a function which it accepts an integer and returns an integer
    * In this function, 10 is hard coded
    */
  def higherOrderFunction(action: Int => Int): String = action(10).toString

  def higherOrderFunction2(action: Int => Int, in: Int): Int = action(in)

  println(higherOrderFunction(Scala15Function.addOne))
  println(higherOrderFunction(minusOne))
  println(minusOne(10))

  // Exactly the same
  println(higherOrderFunction2(minusOne, 10))
  println(minusOne(10))

  /*
  Could you give me an example of a higher order function we used in this session?
   */
  def minusOne(in: Int): Int = in - 1

}
