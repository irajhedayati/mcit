package ca.mcit.bigdata.scala.function

/**
 * It is possible to define a function within another function. This is to limit the scope of a function.
 */
object NestedFunction extends App {

  val hasOdd: List[Int] => Boolean = (xs: List[Int]) => {
    val isOdd = (x: Int) => (x % 2) != 0
    xs exists isOdd
  }

  hasOdd(List(2,4,8))
  hasOdd(List(2,3,8))
  hasOdd(List(5,3))

}
