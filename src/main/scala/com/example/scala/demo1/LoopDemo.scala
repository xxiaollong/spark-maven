package com.example.scala.demo1

import scala.util.control.Breaks._


/**
  * 循环:
  * for
  * while
  */
object LoopDemo {

  def main(args: Array[String]): Unit = {
    //    forDemo01()
    //    forDemo02()
    //    forDemo03()
    //    forDemo04()
    //    forDemo05()
    //    forDemo06()
    //    forDemo07()
    //    whileDemo01()
    //    dowhileDemo01()
    //    cfbDemo()
    //    breakDemo01()
    breakDemo02()
  }

  // 使用循环守卫实现continue
  def continueDemo01(): Unit = {

  }

  // 循环守卫中实现break
  def breakDemo02(): Unit = {
    var flag = true
    var sum = 0

    for (i <- 1 to 100; if flag) {
      sum += i
      println(i + " : " + sum)
      if (sum >= 20) flag = false
    }

  }

  // break: break()-breakable()
  def breakDemo01(): Unit = {
    var i = 1
    breakable(
      while (true) {
        i += 1
        printf("i = %d \r\n", i)
        if (i == 10) break()
      }
    )
    println("while OK")

    println("----------")
    breakable {
      for (i <- 1 to 20) {
        printf("i = %d \r\n", i)
        if (i == 10) break()
      }
    }

    println("for OK")

  }

  def cfbDemo(): Unit = {
    for {i <- 1 to 9
         j <- 1 to i} {

      printf("%d * %d = %d \t", j, i, i * j)
      if (i == j) printf("\r\n")

    }
  }

  def dowhileDemo01(): Unit = {
    var i = 1
    do {
      println("Hello " + i)
      i += 1
    } while (i <= 10)
  }


  // while循环
  def whileDemo01(): Unit = {
    var i = 1
    while (i <= 10) {
      println("Hello " + i)
      i += 1
    }

  }

  // for-步长：Range(开始，结束，步长)
  // 注意：Range() 的范围等价于：until，即前闭后开
  // 使用循环守卫也能实现步长
  def forDemo07(): Unit = {
    for (i <- Range(1, 10, 2)) {
      println(i)
    }

    println("-----")

    for (i <- Range(1, 9, 2)) {
      println(i)
    }

    println("-----")
    for (i <- 1 to 10; if i % 2 == 1) {
      println(i)
    }

  }

  // for-{}的使用
  def forDemo06(): Unit = {
    for {i <- 1 to 3
         j <- 4 to 6} {
      printf("%d, %d \r\n", i, j)
    }
  }

  // for-yield, 返回一个Vector集合
  def forDemo05(): Unit = {
    val res1 = for (i <- 1 to 3) yield i
    println(res1)

    val res2 = for (i <- 1 to 3) yield {
      i + 2
    }
    println(res2)
  }

  // for-嵌套循环
  def forDemo04(): Unit = {
    for (i <- 1 to 3; j <- 4 to 6) {
      printf("%d, %d \r\n", i, j)
    }
  }

  // for-引入变量
  def forDemo03(): Unit = {
    for (i <- 1 to 3; j = i + 1) {
      println(j)
    }

    println("------------")

    // 循环守卫+引入变量
    for (i <- 1 to 10; if i % 2 == 0; j = i * i) {
      println(j)
    }
  }

  // for-循环守卫
  def forDemo02(): Unit = {
    for (i <- 1 to 10; if i % 2 == 0) {
      println(i)
    }
  }

  // for循环推导式的使用
  def forDemo01(): Unit = {
    val start: Int = 1
    val end: Int = 10

    // to 遍历的范围为[start, end]
    for (i <- start to end) {
      println("to " + i)
    }

    // until 遍历的范围为[start, end)
    for (i <- start until end) {
      println("until " + i)
    }

    // 遍历集合
    val list: List[Any] = List("hello", 20, true)
    for (item <- list) {
      println(item)
    }

    // 遍历集合
    val map: Map[Int, String] = Map(1 -> "11", 2 -> "22")
    for ((k, v) <- map) {
      println(k + " : " + v)
    }

  }

}
