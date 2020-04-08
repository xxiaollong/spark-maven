package com.example.scala.demo7

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

/**
  * 集合练习题
  *
  *
  */
object AllSetTask01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()

  }

  // 统计一句话中各个字母出现的次数
  def demo2(): Unit = {
    val str = "AAAABBBBBCCCDD"
    val map = str.foldLeft(Map[Char, Int]())(charCount)
    for ((k, v) <- map) {
      println(s"$k -> $v")
    }

  }

  def charCount(map: Map[Char, Int], c: Char): Map[Char, Int] = {
    map(c) = map.getOrElse(c, 0) + 1
    map
  }


  // 只用折叠将一个字符串打散放入ListBuffer中
  def demo1(): Unit = {
    val str = "AAABBBCCCDDD"
    val lbf = ListBuffer[Char]()
    str.foldLeft(lbf)(putArr)
    println("lbf = " + lbf)
  }

  def putArr(arr: ListBuffer[Char], c: Char): ListBuffer[Char] = {
    arr += c
    arr
  }


}
