package com.example.scala.demo3

/**
  * 抽象类，抽象属性，抽象方法
  *
  * 匿名子类
  */
object AbstractClassDemo01 {
  def main(args: Array[String]): Unit = {
    println("11")

    // 抽象类不能实例化
    // val a1 = new A001

    // 抽象类在实例化时动态实现抽象属性和抽象方法
    // 相当于匿名子类
    val a2 = new A001 {
      // 抽象方法
      override def hello(): Unit = println("hello")

      override val name: String = ""
    }
    a2.say()
    a2.hello()

    println("---------------")

    // 实例化抽象类的实现类
    val a3: A001 = new A001_S
    a3.say()
    println(a3.name)
    a3.hello()

  }
}

// 抽象类使用：abstract 标记
abstract class A001 {
  // 抽象属性
  val name: String

  // 普通属性
  val age: Int = 10

  // 抽象方法，不能包含方法体
  def hello()

  // 抽象类中可以包含普通方法
  def say(): Unit = {
    println("Hi..")
  }
}

// A001_S类实现了抽象类A001
class A001_S extends A001 {

  // 实现类中 override 关键字可以省略

  // override val name: String = "hah"
  val name: String = "hah"

  // 抽象方法，不能包含方法体
  //  override def hello(): Unit = {
  //    println("hello")
  //  }
  def hello(): Unit = {
    println("hello")
  }
}
