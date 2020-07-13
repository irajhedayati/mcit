package ca.mcit.bigdata.hadoop

import org.apache.hadoop.fs.Path
import scala.io.Source

object HadoopEnricher extends HdfsClient {

  val routeStream = fs.open(new Path("/user/summer2019/dheeraj/routes.txt"))
  val routeMap: Map[Int, Route] = Iterator.continually(routeStream.readLine()).takeWhile(_ != null)
    .toList
    .tail
    .map(_.split(",", -1))
    .map(p => p(0).toInt -> Route(p(0).toInt, p(1), p(2), p(3), p(4), p(5), p(6),p(7)))
    .toMap
  routeStream.close()

  val tripsStream = fs.open(new Path("/user/summer2019/dheeraj/trips.txt"))
  val outPath = new Path("/user/summer2019/dheeraj/trips_routes.txt")
  val outputStream = fs.append(outPath)
  val x: Iterator[String] = Iterator.continually(tripsStream.readLine()).takeWhile(_ != null)
  while(x.hasNext) {
    val record: String = x.next()
    val p: Array[String] = record.split(",", -1)
    if (p(0) != "route_id") {
      val trip = Trip(p(0).toInt, p(1), p(2), p(3), p(4).toInt, p(5).toInt, p(6).toInt,
        if (p(7).isEmpty) None else Some(p(7)),
        if (p(8).isEmpty) None else Some(p(8)))
      val route = routeMap.get(trip.route_id)
      RouteTrip(trip, route)
      outputStream.writeUTF(Trip.toCsv(RouteTrip(trip, route).trip))
    }
  }
  outputStream.close()
  tripsStream.close()

  fs.close()
}
