package ca.mcit.bigdata.hadoop

case class Route(
                  route_id: Int,
                  agency_id: String,
                  route_short_name: String,
                  route_long_name: String,
                  route_type: String,
                  route_url: String,
                  route_color: String,
                  route_text_color: String
                )

case class RouteTrip(trip: Trip, route: Option[Route])
