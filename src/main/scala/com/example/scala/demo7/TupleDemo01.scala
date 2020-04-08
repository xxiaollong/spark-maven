package com.example.scala.demo7

/**
  * 元组
  *   总共有22个类
  *   分别为：Tuple1 -- Tuple22
  *   对应为：可以包含元素的个数
  *
  */
object TupleDemo01 {
  def main(args: Array[String]): Unit = {
    demo1()
  }

  // 创建元组
  def demo1(): Unit ={

    // 创建
    val t1 = (10, 34.9, true, "hello")
    println(t1)

    // 获取元素--使用_的方式（对应为第几个元素）
    println("t1._1  = " + t1._1)
    println("t1._4  = " + t1._4)

    // 获取元素--使用下标的方式
    println("t1(0) = " + t1.productElement(0))
    println("t1(3) = " + t1.productElement(3))

    // 遍历--需要使用迭代器遍历
    for (item <- t1.productIterator){
      println("itme = " + item)
    }


  }

}
