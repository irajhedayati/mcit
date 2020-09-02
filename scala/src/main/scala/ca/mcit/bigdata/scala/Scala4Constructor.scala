package ca.mcit.bigdata.scala

object Scala5Constructor extends App {
  new Address(2300, "Guy st.", "A3A4H5")
  new Address("Guy st.", "A3A4H5")
}

class Address(streetNumber: Int, streetName: String, zipCode: String) {

  def this(streetName: String, zipCode: String) = this(1, streetName, zipCode)
  def this(zipCode: String) = this(1, null, zipCode)

  println(s"$streetNumber $streetName")

}

/*
public class Address {
  private int streetNumber;
  private String streetName;

  public Address(int streetNumber, String streetName) {
    this.streetNumber = streetNumber;
    this.streetName = streetName;
  }

  public Address(String streetName) {
    this.streetNumber = 1;
    this.streetName = streetName;
  }

}
 */
