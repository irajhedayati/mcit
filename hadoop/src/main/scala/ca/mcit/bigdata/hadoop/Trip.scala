package ca.mcit.bigdata.hadoop

case class Trip(route_id: Int,
                service_id: String,
                trip_id: String,
                trip_headsign: String,
                direction_id: Int,
                shape_id: Int,
                wheelchair_accessible: Int,
                note_fr: Option[String],
                note_en: Option[String]
               )

object Trip {
  def toCsv(trip: Trip): String = {
    trip.route_id + "," +
      trip.service_id + "," +
      trip.trip_id + "," +
      trip.trip_headsign + "," +
      trip.direction_id + "," +
      trip.shape_id + "," +
      trip.wheelchair_accessible + "," +
      trip.note_fr.getOrElse("") + "," +
      trip.note_en.getOrElse("")
  }
}
