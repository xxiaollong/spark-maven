package com.example.scala.demo9

import java.util.Date


/**
  * 递归
  *
  *
  */
object RecursiveDemo01 {

  def main(args: Array[String]): Unit = {

    //    demo1()
    //    demo2()
    //    demo3()
    demo4()

  }

  // 递归求阶乘
  def demo4(): Unit = {
    val n = 5
    println(getFactorial(n))
  }

  // 递归求阶乘
  def getFactorial(n: Int): Int = {
    if (n == 0) {
      return 1
    }
    n * getFactorial(n - 1)
  }

  // 递归反转字符串
  def demo3(): Unit = {
    val str = "ABCDE"
    println(reverseFun(str))

  }

  // 递归反转字符串
  def reverseFun(str: String): String = {
    if (str.length == 1) {
      str
    } else {
      reverseFun(str.tail) + str.head
    }
  }

  // 求list的最大值
  def demo2(): Unit = {
    val list = List(10, 6, 77, 90, 2, 5, 1)
    list.max
    val res = getMax(list)
    println("max = " + res)

  }

  // 递归求list最大值
  def getMax(list: List[Int]): Int = {
    if (list.isEmpty) {
      throw new Exception("数组为空")
    } else if (list.size == 1) {
      println("1")
      list.head
    } else if (list.head > getMax(list.tail)) {
      println("2")
      list.head
    } else {
      println("3")
      getMax(list.tail)
    }
  }

  // 递归和循环效率比较 -- 递归求和
  def demo1(): Unit = {

    val max = BigInt(9999)

    var num1 = BigInt(1)
    var res1 = BigInt(0)

    val d1: Long = new Date().getTime

    // 执行循环
    while (num1 <= max) {
      res1 += num1
      num1 += 1
    }

    val d2: Long = new Date().getTime
    println("while: " + res1 + " 用时 " + (d2 - d1))

    def sumFun(n: BigInt, sum: BigInt): BigInt = {
      if (n <= max) {
        sumFun(n + 1, sum + n)
      } else {
        sum
      }
    }

    // 这种递归层数太深会报内存溢出
    def sumFun1(n: BigInt): BigInt = {
      if (n == max) {
        return n
      }

      sumFun1(n + 1) + n
    }


    // 递归测试
    var res2 = BigInt(0)
    val d3: Long = new Date().getTime
    //    res2 = sumFun(1, res2)
    res2 = sumFun1(1)
    val d4: Long = new Date().getTime
    println("递归: " + res2 + " 用时 " + (d4 - d3))

  }


}
