package ca.mcit.bigdata.scala

trait Shape {
  def draw(): String
  def drawToConsole(): Unit = println(draw())
}
// override vs overload
class Circle(r: Double) extends Shape {
  override def draw(): String = s"This is a circle of radius $r with area of ${area()}"
  def area(): Double = Circle.pi * r * r
}
object Circle {
  val pi = 3.14
}

class Cylinder(r: Double, h: Double) extends Circle(r) {
  override def draw(): String = s"This is a cylinder with area of ${area()}"
  override def area(): Double = super.area() * 2 + (h * 2 * Circle.pi * r)
}


