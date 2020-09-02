package ca.mcit.bigdata.scala

/**
  * 1. toString override needed for a class
  * 2. attributes are private by default, need to use val/var
  * 3. equals needed for a class to compare objects properly
  * 4. in order to keep objects immutable, need to instantiate new object
  * 5. case class solves all the problems
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