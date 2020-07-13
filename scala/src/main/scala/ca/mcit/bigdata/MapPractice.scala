package ca.mcit.bigdata

object MapPractice extends App {

  val romanNumbersList: List[Int] = List(1, 2, 3, 4)

  /** A dictionary to translate Roman numbers to Modern */
  val romanNumbers: Map[String, Int] = Map(
    new Tuple2("I", 1),
    Tuple2("II", 2),
    "III" -> 3,
    ("IV", 4)
  )

//  romanNumbers.get("I").foreach(println)
  romanNumbers.get("II")
    .map(x => x * 2)
    .foreach(println)
//  romanNumbers.get("III").foreach(println)
//  romanNumbers.get("IV").foreach(println)
  romanNumbers.get("V").map(x => x * 2).foreach(println)

  val v: Option[Int] = romanNumbers.get("IV")
  println(v.getOrElse(0))
  println(romanNumbers.getOrElse("V", 0))

  if (v.isDefined) {
    println(v.get)
  } else {
    println(0)
  }

  v match {
    case Some(x) => println(s"The value of V is defined and it is $x")
    case None => println(s"The value of V is not defined.")
  }


  /** Option */
//  val v: Option[Int] = romanNumbers.get("V")
//  val i: Option[Int] = romanNumbers.get("I")
//
//  println(v.isDefined)
//  println(i.isDefined)

}
