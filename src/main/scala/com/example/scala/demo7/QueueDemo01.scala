package com.example.scala.demo7

import scala.collection.mutable

/**
  * 队列
  *
  *   可变队列
  *     出队、入队会直接改变当前队列
  *
  *   不可变队列
  *
  */
object QueueDemo01 {
  def main(args: Array[String]): Unit = {
    demo1()

  }

  // 不可变队列
  def demo2(): Unit ={

  }


  // 可变队列
  def demo1(): Unit ={
    // 创建
    val qu1 = new mutable.Queue[Int]
    val qu2 = mutable.Queue[Int]()
    val qu3 = mutable.Queue(12, 35.6, "hello")

    // 添加元素
    qu1 += 10
    println("qu1 = " + qu1)

    // 添加一个list
    qu1 ++= List(20, 30, 40)
    println("qu1 = " + qu1)

    // 修改元素
    qu1(0) = 100
    println("qu1 = " + qu1)

    // 获取元素   不改变当前队列
    println("qu1.head = " + qu1.head)
    println("qu1.last = " + qu1.last)
    // 获取队尾，除第一个元素的一个队列
    println("qu1.tail = " + qu1.tail)
    println("qu1(1) = " + qu1(1))
    println("qu1 = " + qu1)

    // 入队 默认从尾部加入元素
    qu1.enqueue(500, 600)
    println("qu1 = " + qu1)

    // 出队 默认从头部取出元素
    val item = qu1.dequeue()
    println("item = " + item)
    println("qu1 = " + qu1)

    // 遍历
    for (item <- qu1){
      println("item = " + item)
    }

  }

}
