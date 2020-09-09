package ca.mcit.bigdata.scala

object Scala16NestedFunction extends App {

  /** if the input list has any odd number
    *
    * Exists: if there exists any member in the collection which the condition evaluates to true
    **/
  //  def hasOdd(in: List[Int]): Boolean = in.filter(isOdd).nonEmpty
  def hasOdd(in: List[Int]): Boolean = {
    /** if the input is an odd number
      *
      * This is nested function and its scope is only 'hasOdd'
      **/
    def isOdd(in: Int): Boolean = in % 2 == 1

    in.exists(isOdd)
  }

  println("==== Test hasOdd")
  println(hasOdd(List(2, 3, 8)))
  println(hasOdd(List(2, 4, 8)))

}
