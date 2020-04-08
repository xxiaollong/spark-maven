package com.example.scala.demo2

/**
  * 函数和方法
  *
  */
object Methed2Function {

  def main(args: Array[String]): Unit = {
    val t1: Test1 = new Test1()
    println(t1.sum(10, 20))
    val f1 = t1.sum _
    println("f1 = " + f1)
    println(f1(10, 20))

    val f2 = (n1: Int, n2: Int) => {
      n1 + n2
    }

    println("f2 = " + f2)
    println(f2(10, 20))

  }
}

class Test1 {
  def sum(n1: Int, n2: Int): Int = {
    n1 + n2
  }
}