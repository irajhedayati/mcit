package ca.mcit.bigdata

object ForYield extends App {

  val x1 = List(1, 2, 3, 4)
  val x2 = List("I", "II", "IV", "V")

  for (a <- x1) {
    println(a)
    for (b <- x2) {
      println(s"  $b")
    }
  }


  val left1: Map[Int, String]       = Map (1 -> "a",    2 -> "b")

  val left: List[(Int, String)]     = List(1 -> "a",    2 -> "b")
  val right: List[(String, String)] = List("a" -> "i", "c" -> "iii")

  for(l <- left) {
    for(r <- right) {
      if (l._2 == r._1)
        println(s"[$l, $r]")
    }
  }

  for {
    l <- left
    r <- right
    if l._2 == r._1 // inner join
  } println(s"[$l, $r]")

  val innerJoin =
    for {
      l <- left
      r <- right
      if l._2 == r._1 // inner join
    } yield (l._1, l._2, r._1, r._2)

  innerJoin foreach println


  val x =
    for {
      i <- 1 to 10
//      if i % 2 == 1
      j <- 1 to 10
//      if j % 2 == 0
//      if i == j
    } yield s" $i x $j = ${i * j}"

  for (i <- 1 to 10) {
    for (j <- 1 to 10) {
      if (i % 2 == 1 && i ==j)
        println(s" $i x $j = ${i * j}")
    }
  }

  for (i <- 1 to 10) {
    if (i % 2 == 1) {
      for (j <- 1 to 10) {
        if (i == j)
          println(s" $i x $j = ${i * j}")
      }
    }
  }

  x foreach println




}
