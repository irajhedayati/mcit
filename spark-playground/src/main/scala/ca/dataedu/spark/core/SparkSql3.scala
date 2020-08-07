package ca.dataedu.spark.core

import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkSql3 extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Spark SQL practices")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._
  case class Employee(lastName: String, deptId: Option[Int])
  val employees = spark.sparkContext
    .parallelize(Array[(String, Option[Int])](
      ("Rafferty", Some(31)), ("Jones", Some(33)), ("Heisenberg", Some(33)), ("Robinson", Some(34)), ("Smith", Some(34)), ("Williams", null)
    ))
    .toDF("lastName", "deptId")
    .as[Employee]
//  employees.show(10)
  /*
  +----------+------+
|  lastName|deptId|
+----------+------+
|  Rafferty|    31|
|     Jones|    33|
|Heisenberg|    33|
|  Robinson|    34|
|     Smith|    34|
|  Williams|  null|
+----------+------+
+------+-----------+
|deptId|   deptName|
+------+-----------+
|    31|      Sales|
|    33|Engineering|
|    34|   Clerical|
|    35|  Marketing|
+------+-----------+
+------+----------+-----------+
|deptId|  lastName|   deptName|
+------+----------+-----------+
|    31|  Rafferty|      Sales|
|    34|  Robinson|   Clerical|
|    34|     Smith|   Clerical|
|  null|  Williams|       null|
|    33|     Jones|Engineering|
|    33|Heisenberg|Engineering|
+------+----------+-----------+
   */

  case class Department(deptId: String, deptName: String)
  val departments = spark.sparkContext
    .parallelize(Array(
      (31, "Sales"), (33, "Engineering"), (34, "Clerical"),
      (35, "Marketing")
    ))
    .toDF("deptId", "deptName")
    .as[Department]
//  departments.show(10)

  val empDept = employees.join(departments, Seq("deptId"), "left_outer")
//  empDept.write.json("/user/iraj/emp_dept.json")
  empDept.coalesce(2).write.mode(SaveMode.Overwrite).json("/user/iraj/emp_dept.json")
  while(true) Thread.sleep(1000)
  spark.close()

}
