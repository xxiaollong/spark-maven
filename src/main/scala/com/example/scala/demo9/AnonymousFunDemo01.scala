package com.example.scala.demo9

/**
  *
  * 匿名函数
  *
  * 高阶函数
  *
  */
object AnonymousFunDemo01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    //    demo2()
    //    demo3()
    //    demo4()
    //    demo5()
    demo6()
  }

  // 高阶函数的使用，简写方式
  def demo6(): Unit = {
    val list = List(2, 3, 4, 5)
    // x 表示传入的每个元素
    println(list.map((x: Int) => x + 1))
    // 使用类型推导
    println(list.map((x) => x + 1))
    // 只有1个入参，可省略()
    println(list.map(x => x + 1))
    // x在右边出现了1次，可以使用_替代
    println(list.map(_ + 1))

    // 简写示例
    println(list.reduce((n1:Int, n2:Int) => n1 + n2))
    // 类型推断
    println(list.reduce((n1, n2) => n1 + n2))
    // _替代右边只出现1次的参数
    println(list.reduce( _ + _))
    println(list.sum)

  }

  // 高阶函数的使用，返回一个函数
  // 返回值为函数是闭包实现的基础
  def demo5(): Unit = {

    // 定义了一个高阶函数，返回值为一个函数
    def f1(n1: Int) = {
      // 返回的函数为：f(n2: Int) => n1 - n2
      (n2: Int) => n1 - n2
    }

    // res1的类型为：<function1>
    // res1 = (n2: Int) => 10 - n2
    val res1 = f1(10)
    println("res1 = " + res1)

    val res2 = res1(3)
    println("res2 = " + res2)

    // 简单调用方式
    val res3 = f1(10)(3)
    println("res3 = " + res3)

  }

  // 高阶函数的使用，同时传入两个函数参数
  def demo4(): Unit = {
    // 定义了一个高阶函数
    def test(f1: Int => Int, f2: Double => Int, n1: Double): Int = {
      f1(f2(n1))
    }

    // 普通函数，可以当做参数传递给高阶函数使用
    def sum(n: Int): Int = {
      n + n
    }

    def getIntNum(n: Double): Int = {
      n.toInt
    }


    println(test(sum, getIntNum, 10.26))

  }

  // 高阶函数的基本使用
  def demo3(): Unit = {
    // 定义了一个高阶函数
    // f: Int => Int 表示一个入参出参都为Int类型的函数
    // n1: Int 表示一个Int类型的形参
    def test(f: Int => Int, n1: Int): Int = {
      f(n1)
    }

    // 普通函数，可以当做参数传递给高阶函数使用
    def sum(n: Int): Int = {
      n + n
    }


    println(test(sum, 10))

  }


  // 定义一个匿名函数，求两个数的和并打印函数类型
  def demo2(): Unit = {
    val f1 = (n1: Int, n2: Int) => n1 + n2

    println("f1 的类型为：" + f1)
    println("f1(2,3) = " + f1(2, 3))

  }

  // 匿名函数简介
  def demo1(): Unit = {
    // val f1 代表指向匿名函数的一个变量
    // (n: Int) => n * n 即为匿名函数，
    // (n: Int)是参数列表
    // n * n为函数体
    // 返回值类型通过类型推导实现

    val f1 = (n: Int) => n * n
    val res = f1(3)
    println("res = " + res)

  }

}
