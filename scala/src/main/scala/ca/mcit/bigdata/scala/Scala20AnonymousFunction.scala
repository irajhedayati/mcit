package ca.mcit.bigdata.scala

/** A function that doesn't have a name */
object Scala20AnonymousFunction extends App {

  /** Increment the input by one */
  def increment(x: Int): Int = x + 1

  val incrementedValue = Scala17HigherOrderFunction.higherOrderFunction(increment)
  println(incrementedValue)

  val incrementedValue2 = Scala17HigherOrderFunction.higherOrderFunction((x: Int) => x + 1)
  println(incrementedValue)

  (1 to 10).map((x: Int) => x + 1).filter(_ % 2 == 1).foreach(println)
  (1 to 10).map(x => x + 1)       .filter(_ % 2 == 1).foreach(println)
  (1 to 10).map(_ + 1)            .filter(_ % 2 == 1).foreach(println)

  (1 to 10).map(_ + 1).filter(x => x % 2 == 1).foreach(println)
}
