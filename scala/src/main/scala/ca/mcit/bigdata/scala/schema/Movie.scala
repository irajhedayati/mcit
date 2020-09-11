package ca.mcit.bigdata.scala.schema

// Internal data model
// Schema
case class Movie(mID: Int, title: String, year: Int, director: String)
case class Rating(rID: Int, mID: Int, stars: Int)
case class EnrichedRating(rating: Rating, movie: Movie)