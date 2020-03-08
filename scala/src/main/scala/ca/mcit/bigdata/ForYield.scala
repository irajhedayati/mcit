package ca.mcit.bigdata

object ForYield extends App {

  val left: Seq[(Int, String)] = List(1 -> "a", 2 -> "b")
  val right: Seq[(String, String)] = List("a" -> "i", "c" -> "iii")

//  for(l <- left) {
//    for(r <- right) {
//      if (l._2 == r._1)
//        println(s"[$l, $r]")
//    }
//  }

//  val innerJoin: Seq[((Int, String), (String, String))] = for {
//    l <- left
//    r <- right
//    if (l._2 == r._1) // inner join
//  } yield l -> r
//
//  innerJoin foreach println


  val x = for {
    i <- 1 to 10
    if i % 2 == 1
    j <- 1 to 10
//    if j % 2 == 0
    if i == j
  } yield s" $i x $j = ${i * j}"

  x foreach println




}
