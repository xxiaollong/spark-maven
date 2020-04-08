package com.example.scala.demo2

/**
  * 递归练习
  *
  *
  */
object RecursiveTask {

  def main(args: Array[String]): Unit = {
    //    fbnDemo(10)
    //    fbnArr(10)
    //    fbnNum(10)
    //    println("f(10) = " + fun01(10))
    //    println(fun02())
    //    println(fun02_2())
  }

  // 猴子吃桃子问题，求总共摘了多少桃子--循环解决
  def fun02_2(n: Int = 10): Int = {
    var num = 1

    for (i <- 1 until n) {
      num = (num + 1) * 2
    }

    num
  }

  // 猴子吃桃子问题，求总共摘了多少桃子--递归解决
  def fun02(n: Int = 1): Int = {
    if (n == 10) {
      return 1
    }
    (fun02(n + 1) + 1) * 2
  }

  // 如果f(1)=3, f(n)=2*f(n-1)+1, 求f(n)的值
  def fun01(n: Int): Int = {
    if (n == 1) {
      return 3
    }
    2 * fun01(n - 1) + 1
  }

  // 使用数字实现斐波那契数列
  def fbnNum(n: Int): Unit = {
    var a = 1
    var b = 1
    val max = Math.round(n.toFloat / 2)
    // 一次实现2个，相当于步长为2
    for (i <- 1 to max) {
      if (i * 2 < n) {
        printf("%d,%d,", a, b)
      } else if (i * 2 == n) {
        printf("%d,%d", a, b)
      } else {
        printf("%d", a)
      }
      a = a + b
      b = a + b
    }


  }

  // 使用数组实现斐波那契数列
  def fbnArr(n: Int): Unit = {
    val arr: Array[Int] = new Array[Int](n)
    arr(0) = 1
    arr(1) = 1
    for (i <- 2 until arr.length) {
      arr(i) = arr(i - 1) + arr(i - 2)
    }

    println(arr.mkString(","))
  }

  def fbnDemo(n: Int): Unit = {
    for (i <- 1 to n) {
      if (i == n) {
        print(fbn(i))
      } else {
        print(fbn(i) + ",")
      }
    }
  }

  // 斐波那契数列
  def fbn(n: Int): Int = {
    if (n == 1 || n == 2) {
      return 1
    }
    fbn(n - 2) + fbn(n - 1)
  }




}
