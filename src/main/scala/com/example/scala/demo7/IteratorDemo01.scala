package com.example.scala.demo7

/**
  * 迭代器
  */
object IteratorDemo01 {
  def main(args: Array[String]): Unit = {
    demo1()
  }

  def  demo1(): Unit ={
    val list = List(1,2,3)
    val iteration = list.iterator

    // 使用while循环
    while (iteration.hasNext){
      println(iteration.next())
    }

    // 重置迭代器指针


    println("----------")

    // 使用for循环
    val iteration1 = list.iterator
    for(item <- iteration1){
      println(item)
    }
  }
}
