package ca.mcit.bigdata.scala

/**
  * Scala "case class" helps us to model immutable data with less code overhead.
  *
  * To see the benefits and also make a connection, the same behavior can be
  * implemented using normal class if:
  * {{{
  *   1. We override `toString` function to print data they hold instead of
  *   object reference
  *   2. Add var/val to make attributes public and accessible
  *   3. We override `equals` to be able to compare to objects properly
  *   4. in order to keep objects immutable, need to instantiate new object
  * }}}
  *
  * Also, with normal classes we need `new` keyword to instantiate a new object
  * while the "case class" doesn't need that (less code). On the other hand,
  * to derive  a new instance (change one of the attributes and get a new instance),
  * we should create a new instance and then copy all the values while "case class"
  * provides a `copy` method that you can easily achieve this.
  */
object Scala8CaseClass extends App {

  // We instantiated a person called "John" age 30
  val john = new Person2("John", 30)
  val joe = new Person2("Joe", 25)

  println(john)
  println(joe)
  /*
  original toString
  ca.mcit.bigdata.scala.Person2@6108b2d7
  ca.mcit.bigdata.scala.Person2@1554909b

  with overriden toString:
  Person2(John,30)
  Person2(Joe,25)
   */

  println(s"John is ${john.age} years old")

  /*
    Java
    "string1" == "string2"      : compare references
    "string1".equals("string2") : calls 'equals'

    Scala
    "string1" == "string2"      : calls 'equals'
    "string1".equals("string2") : calls 'equals'
 */
  val john2 = new Person2("John", 30)
  println(john == john2)

  val agedJohn = new Person2(john.name, john.age + 5)
  println(agedJohn)

  val john3 = Person3("John", 30)
  val joe3 = Person3("Joe", 25)
  println(john3)
  println(joe3)
  val john4 = Person3("John", 30)
  println(john3 == john4)
  val agedJohn2 = john3.copy(age = 35)
  println(agedJohn2)
}

// You need var or val to make a property public
// the property with val is immutable
// This doesn't work; john.name = "Joe"
// the property with var is mutable
// This works; john.age = 35
class Person2(val name: String, var age: Int) {
  override def toString: String = s"Person2($name,$age)"

  override def equals(obj: Any): Boolean =
    name == obj.asInstanceOf[Person2].name && age == obj.asInstanceOf[Person2].age
}

case class Person3(name: String, age: Int)

/*
Equivalent code in Java
public class Person2Java {

  private String name;
  private int age;

  public Person2(String name, int age) {
    name = name;
    age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return "Person2(name=" + name + ", age=" + age + ")";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj instanceOf Person2) {
      Person2 o = (Person2) obj;
      if (o.name == this.name && o.age == this.age) return true
      else return false;
    } else {
      return false
    }
  }

}
*/