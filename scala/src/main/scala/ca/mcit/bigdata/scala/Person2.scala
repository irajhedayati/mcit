package ca.mcit.bigdata.scala

import com.sun.org.omg.CORBA.Repository

// 1. access modifier
// 2. print data
// 3. comparison
// 4. object instantiation
// 5. immutability

// class Person2(name: String, age: Int)

class Person2(val name: String, val age: Int) {
  override def toString: String = s"Person2(name=$name, age=$age)"

  override def equals(obj: Any): Boolean = {
    if (obj == null) false
    else if (!obj.isInstanceOf[Person2]) false
    else {
      val o = obj.asInstanceOf[Person2]
      name == o.name && age == o.age
    }
  }

}


class Person3(_name: String) {
  val name = _name
}

class Person4(val name: String)

object Test {
  new Person3("John").name
  new Person4("John").name
}
/*
POJO : Plain Old Java Object
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

object Iraj extends App {
//  println("Instantiate a person")
  val person1: Person2 = new Person2("John", 20)
//  val person2: Person2 = new Person2("John", 20)

//  println(person1 == person2) // true or false?

  // println("New person is " + person1.name + " with age of " + person1.age)
  // String interpolation
//  val y = 40
//  println(s"New person is ${person1.name} with age of ${person1.age} which is less than $y")
//  val p1 = Person("John", 20)
//  val p2 = Person("John", 20)
//  val p3 = p1.copy(name = "Joe").copy(age = 10)
//  println(p1)
//  println(p3)
//  println(p1)

  println(Person("John,20"))
  // pattern matching

}
