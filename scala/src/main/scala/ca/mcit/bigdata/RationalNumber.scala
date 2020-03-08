package ca.mcit.bigdata

class RationalNumber(val x: Int, val y: Int) {

  def plus(o: RationalNumber): RationalNumber = new RationalNumber(x * o.y + o.x * y, y * o.y)
  def minus(o: RationalNumber): RationalNumber = new RationalNumber(x * o.y - o.x * y, y * o.y)
  def multiply(o: RationalNumber): RationalNumber = new RationalNumber(x * o.x, y * o.y)
  def divide(o: RationalNumber): RationalNumber = new RationalNumber(x * o.y, y * o.x)
  def negation(): RationalNumber = new RationalNumber(-x, y)

}
