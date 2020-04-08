package com.example.scala.demo7

import scala.collection.mutable

/**
  * Set集合
  * 元素不重复，无序，默认哈希实现
  *
  * 不可变Set: 默认
  *
  * 可变Set
  *
  */
object SetDemo01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()

  }

  // 可变Set
  def demo2(): Unit = {
    // 创建
    val set1 = mutable.Set[Any]()
    val set2 = mutable.Set(10, true, "str")

    println("set2 = " + set2)

    // 添加元素
    set2.add("hello")
    set2.add("hello", 30) // 使用add方法这样添加的是一个元组
    set2 += (10, false, true)

    println("set2 = " + set2)


    // 删除元素--按值删除
    set2.remove("hello")
    set2 -= (false, true)
    set2.remove("hello", 30) // 删除元组
    println("set2 = " + set2)


    // 遍历
    for (itme <- set2){
      println("item = " + itme)
    }


  }

  // 不可变Set
  def demo1(): Unit = {

    // 创建
    val set1 = Set[Any]()
    val set2 = Set(10, true, "str")


  }

}
