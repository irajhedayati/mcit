package ca.mcit.bigdata.scala

import ca.mcit.bigdata.scala.schema.Stop

import scala.io.Source

object StopStart extends App {

  val stopSource = Source.fromFile("/home/bd-user/Documents/stops.txt")
  val stopList = stopSource
    .getLines()
    .toList
    .tail
    .map(_.split(","))
    //.map((l: String) => {l.split(",")})
    .map(p => Stop(p(0), p(1), p(2), p(3).toDouble, p(4).toDouble, p(5), p(6).toInt, p(7), p(8).toInt))
  stopSource.close()

  // How many stops?
  println(s"Total number of stops: ${stopList.length}")
  // How many wheelchair boarding is possible?
  println(s"Total number of stops w/ wheelchair boarding: ${stopList.count(_.wheelchair_boarding == 1)}")
  // How many wheelchair boarding is not possible?
  println("Total number of stops w/o wheelchair boarding: " + stopList.count(_.wheelchair_boarding == 2))


}
