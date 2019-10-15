package ca.mcit.bigdata.schema

case class Stop(
               stop_id: String,
               stop_code: String,
               stop_name: String,
               stop_lat: Double,
               stop_lon: Double,
               stop_url: String,
               location_type: Int,
               parent_station: String,
               wheelchair_boarding: Int
               )
