package ca.mcit.bigdata

trait Shape {
  def draw(): String
}

class Circle(r: Double) extends Shape {
  override def draw(): String = s"This is a circle of radius $r and area of ${area()}"
  def area(): Double = Circle.pi * r * r
}

class Cylinder(h: Double, r: Double) extends Circle(r) {
  override def area(): Double = super.area() * 2 + (h * 2 * Circle.pi * r)
  def areaAsString(): String = s"${this.area()}"
  def parentAreaAsString(): String = s"${super.area()}"
}

object Circle {
  val pi = 3.1415
}

class Square extends Shape {
  override def draw(): String = ???
}

object ShapeMain extends App {
  val c = new Circle(2.0)
  println(c.draw())

  val cl = new Cylinder(3.0, 2.0)
  println(cl.draw())

  println(cl.areaAsString())
  println(cl.parentAreaAsString())
}
