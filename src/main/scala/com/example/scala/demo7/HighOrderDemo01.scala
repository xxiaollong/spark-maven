package com.example.scala.demo7

/**
  * 高阶函数
  *
  */
object HighOrderDemo01 {


  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()
  }

  // 无参无返回值高阶函数示例
  def demo2(): Unit = {
    test2(say)
  }

  // test2 是一个高阶函数
  // f:() => Unit 表示无参，无返回值的函数
  def test2(f: () => Unit): Unit = {
    // 在test2中执行传入的函数
    f()
  }


  def say(): Unit = {
    println("Hi...")
  }


  // 有参有返回值高阶函数示例
  def demo1(): Unit = {

    val res = test(sum2, 3.5)
    println("res = " + res)

    // 也可以这样写
    val res2 = test(sum2 _, 3.5)
    println("res = " + res2)

    // 将一个函数赋值给一个变量
    val f = myShow _
    // 执行对应的函数
    f()

  }


  def myShow(): Unit = {
    println("hello...")
  }

  // test是一个高阶函数
  // f:Double => Double 表示一个函数，接受一个Double，返回一个Double
  // n1:Double 是一个普通参数
  // f(n1) 在test函数中执行，出入参数为n1
  def test(f: Double => Double, n1: Double) = {
    f(n1)
  }

  def sum2(n: Double): Double = {
    n + n
  }

}
