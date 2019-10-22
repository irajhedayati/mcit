package ca.mcit.bigdata

object Main extends App {
  val customer = CustomerUtil("John,30")
  println(customer)
  val CustomerUtil(csv) = customer
  println(csv)
}

case class Customer(name: String, age: Int)

object CustomerUtil {

  def apply(csv: String): Customer = {
    val fields = csv.split(",", -1)
    Customer(fields(0), fields(1).toInt)
  }

  def unapply(customer: Customer): Some[String] =
    Some(s"${customer.name},${customer.age}")
}
