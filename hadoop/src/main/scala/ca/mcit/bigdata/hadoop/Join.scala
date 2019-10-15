package ca.mcit.bigdata.hadoop

trait Join[L, R, Q] {
  def join(a: List[L], b: List[R]): List[Q]
}

case class JoinOutput(left: Any, right: Option[Any])

class GenericNestedLoopJoin[L, R](val joinCond: (L, R) => Boolean) extends Join[L, R, JoinOutput] {
  override def join(a: List[L], b: List[R]): List[JoinOutput] = for {
    i <- a
    j <- b
    if joinCond(i,j)
  } yield JoinOutput(i, Some(j))
}

class LeftMapJoin extends Join[Trip, Route, RouteTrip] {
  override def join(a: List[Trip], b: List[Route]): List[RouteTrip] = {
    // 1. Create a lookup map on routes (b)
    val t: Map[Int, Route] = b.map(route => route.route_id -> route ).toMap
    // 2. Join route and trip and return the result
    a
      .map(trip =>
        if (t.contains(trip.route_id)) RouteTrip(trip, Some(t(trip.route_id)))
        else RouteTrip(trip, None)
      )
  }
}

class MapJoin extends Join[Trip, Route, RouteTrip] {

  override def join(a: List[Trip], b: List[Route]): List[RouteTrip] = {
    // 1. Create a lookup map on routes (b)
    val t: Map[Int, Route] = b.map(route => route.route_id -> route ).toMap

    // 2. Join route and trip and return the result
    a.filter(trip => t.contains(trip.route_id)).map(trip => RouteTrip(trip, Some(t(trip.route_id))))
  }

}
