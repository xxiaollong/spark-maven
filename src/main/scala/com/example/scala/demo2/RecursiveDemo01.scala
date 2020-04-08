package com.example.scala.demo2

/**
  * 递归
  */
object RecursiveDemo01 {

  def main(args: Array[String]): Unit = {
    //    recTest01()
    println(rec03_1(3))
    println(rec03_2(3))
  }

  // 等价写法
  def rec03_2(n: Int): Int = {
    if (n == 1) {
      1
    } else {
      rec03_1(n - 1)
    }

  }
  // 等价写法
  def rec03_1(n: Int): Int = {
    if (n == 1) {
      return 1
    }

    rec03_1(n - 1)
  }

  def recTest01(): Unit = {
    rec01(4)
    println("------")
    rec02(4)
  }

  // 递归
  // rec(4)打印：2，3，4
  def rec01(n: Int): Unit = {
    if (n > 2) {
      rec01(n - 1)
    }
    println("n = " + n)
  }

  // 分支判断
  // rec(4)打印：2
  def rec02(n: Int): Unit = {
    if (n > 2) {
      rec02(n - 1)
    } else {
      println("n = " + n)
    }
  }

}
