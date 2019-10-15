package ca.mcit.bigdata

import ca.mcit.bigdata.schema.Stop

import scala.io.Source

object StopStart extends App {

  val stopList = Source
    .fromFile("/home/bd-user/Documents/stops.txt")
    .getLines()
    .toList
    .tail
    .map(_.split(","))
    //.map((l: String) => {l.split(",")})
    .map(p => Stop(p(0), p(1), p(2), p(3).toDouble, p(4).toDouble, p(5), p(6).toInt, p(7), p(8).toInt))

  // How many stops?
  println(s"Total number of stops: ${stopList.length}")
  // How many wheelchair boarding is possible?
  println(s"Total number of stops w/ wheelchair boarding: ${stopList.filter(_.wheelchair_boarding == 1 ).length}")
  // How many wheelchair boarding is not possible?
  println("Total number of stops w/o wheelchair boarding: " + stopList.count(_.wheelchair_boarding == 2))


}
