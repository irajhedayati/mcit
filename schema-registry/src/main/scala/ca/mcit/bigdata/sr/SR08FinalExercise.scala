package ca.mcit.bigdata.sr

/**
  * Application 1:
  * Produce rating records as Avro messages to `rating` topic
  *
  * Application 2:
  * Read movie into memory and create in-memory lookup table
  * Consume rating from `rating` topic as Avro message
  * Enrich each rating with `movie` in-memory lookup table
  * Produce enriched-rating to Avro topic of `enriched.rating`
  *
  * Console:
  * consume `enriched.rating` topic and verify the result
  */
object SR08FinalExercise {

}
