package ca.mcit.bigdata.scala

import ca.mcit.bigdata.scala.Scala22LookUpTables.Geo

import scala.collection.Map

object Scala24Option extends App {

  val geos: List[Geo] = List(Geo("Montreal", "QC"), Geo("Toronto", "ON"), Geo("Laval", "QC"))
  val geoLookup: Map[String, Geo] = geos.map(geo => geo.city -> geo).toMap

  // Exception in thread "main" java.util.NoSuchElementException: key not found: Ottawa
  // geoLookup("Ottawa")

  val p = geoLookup.getOrElse("Ottawa", "Unknown")
  //  geoLookup.getOrElse("Ottawa", throw new IllegalArgumentException("We don't know which province is Ottawa"))
  println(p)

  val provinceOfOttawa: Option[Geo] = geoLookup.get("Ottawa")
  println(provinceOfOttawa)
  val provinceOfToronto: Option[Geo] = geoLookup.get("Toronto")
  println(provinceOfToronto)

  val nonOption: Option[Geo] = None
  val someOption: Option[Geo] = Some(Geo("Toronto", "ON"))

  geoLookup.get("Toronto") match {
    case Some(geo) => println(geo.province)
    case None => println("We don't have information of Ottawa")
  }

  val q = geoLookup.get("Toronto")
  if (q.isDefined)
    println(q.get.province)
  else
    println("We don't have information of Ottawa")


  val x = geoLookup.get("Ottawa").map(_.province).map(_.toLowerCase())
  println(x)

}
