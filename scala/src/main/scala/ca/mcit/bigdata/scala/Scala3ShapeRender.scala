package ca.mcit.bigdata.scala

object Scala3ShapeRender extends App {

  val c1 = new Circle(2.0)
  c1.drawToConsole()

  val cl1 = new Cylinder(2.0, 3.0)
  cl1.drawToConsole()
}
