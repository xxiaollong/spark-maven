package com.example.scala.demo7

import scala.collection.mutable.ListBuffer

/**
  * 集合
  *
  * List: 不可变集合
  * 注意：
  * 1、List为不可变集合，追加元素会返回一个新集合，集合本身没有变化
  * 2、元素不可修改，不可删除
  *
  * ListBuffer: 可变集合
  *
  */
object ListDemo01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()
  }

  // ListBuffer
  def demo2(): Unit = {
    // 创建
    val lbf1 = new ListBuffer[Int]
    val lbf2 = ListBuffer[Int](1,2,3)
    val lbf3 = ListBuffer(10,39.5,"str",true)

    // 添加元素
    lbf1.append(10)
    println("lbf1 = " + lbf1)
    lbf1 += 20
    println("lbf1 = " + lbf1)
    lbf1 ++= lbf2
    println("lbf1 = " + lbf1)

    // 获取元素
    println("lbf1(1) = " + lbf1(1))

    // 修改元素
    lbf1(0) = 100
    println("lbf1 = " + lbf1)

    // 删除元素--根据下标删除
    lbf1.remove(0)
    println("lbf1 = " + lbf1)

    // 遍历
    for (item <- lbf3){
      println("item = " + item)
    }

  }

  // List
  def demo1(): Unit = {

    // 方式一 指定泛型
    val list1 = List[Int](12, 45, 89)
    // 方式一 不指定泛型
    val list1_2 = List(12, 45, 89, true, "hello")

    // 方式二--创建空集合
    val list2 = Nil

    println("list1: " + list1.toString())

    // 获取元素
    println("list1(1) = " + list1(1))

    // 追加元素--后边追加
    val list1_3 = list1 :+ 100
    println("list1: " + list1.toString())
    println("list1_3: " + list1_3.toString())

    // 追加元素--前边追加
    val list1_4 = 1 +: list1
    println("list1: " + list1.toString())
    println("list1_4: " + list1_4.toString())

    // :: 符号的使用
    // 1、最后必须是一个集合，2、从右向左在后边的集合头部添加元素
    val list4 = List(40, true, "hello")
    val list5 = 1 :: 5 :: false :: list4
    println("list5 = " + list5.toString())
    val list6 = true :: 56.9 :: Nil
    println("list6 = " + list6.toString())
    val list7 = true :: 56.9 :: list4 :: Nil
    println("list6 = " + list7.toString())

    // ::: 符号的使用，将一个集合中的元素添加到一个集合中
    // 1、两边都必须是集合
    val list8 = true :: 56.9 :: list4 ::: Nil
    println("list8 = " + list8)


  }
}
