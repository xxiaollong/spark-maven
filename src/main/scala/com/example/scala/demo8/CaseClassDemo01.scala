package com.example.scala.demo8

/**
  * 样例类（模板类）
  *
  * 1、使用case声明
  * 2、方便对象模式匹配
  * 3、提供多种常用方法
  */
object CaseClassDemo01 {
  def main(args: Array[String]): Unit = {
//    println("hello")
//    demo1()
    demo2()
  }

  // 样例类的copy(),产生一个新对象
  def demo2(): Unit ={
    val c1 = Currency1(20.0, "RMB")
    println(c1)

    val c2 = c1.copy()
    println(c2)

    val c3 = c1.copy(num = 40.0)
    println(c3)

    c1.num = 100.0
    println("c1 : " + c1)
    println("c2 : " + c2)
    println("c3 : " + c3)


  }

  // 样例类的模式匹配
  def demo1(): Unit = {
    val arrs = Array(Dollar(10.0), Currency(20.0, "RMB"), NoAmount)

    for (arr <- arrs) {
      arr match {
        case Dollar(n) => println(s"匹配到了Dollar(n), n=$n")
        case Currency(n, s) => println(s"匹配到了Dollar(n), n=$n, s=$s")
        case NoAmount => println("匹配到了NoAmount")
        case _ => println("什么都没匹配到")
      }

    }


  }
}

// 抽象类
abstract class Amount

case class Dollar(num: Double) extends Amount

// 默认构造函数中的形参是val
case class Currency(num: Double, unit: String) extends Amount
case class Currency1(var num: Double, unit: String) extends Amount

case object NoAmount extends Amount


