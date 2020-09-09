package ca.mcit.bigdata.scala

object Scala13LogicalOperators extends App {

  andTest(10)
  andTest(5)
  andTest(200)
  andTest(100)

  // && AND
  def andTest(x: Int): Unit =
    if (x > 5 && x <= 100) println(s"$x is greater than 5 AND less than equal to 100")

  orTest(10)
  orTest(5)
  orTest(200)
  orTest(100)

  // || OR
  def orTest(x: Int): Unit =
    if (x > 5 || x <= 100) println(s"$x is greater than 5 OR less than equal to 100")

}
