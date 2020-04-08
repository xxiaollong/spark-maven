package com.example.scala.demo7

/**
  * 并行处理
  *
  */
object ParDemo01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()
  }

  // 查看执行的线程名称
  def demo2(): Unit = {

    val res1 = (1 to 100).map(_ => Thread.currentThread().getName).distinct
    println("res1 = " + res1) // 一个线程名称

    val res2 = (1 to 100).par.map(_ => Thread.currentThread().getName).distinct
    println("res2 = " + res2) // 多个线程名称


  }

  // 串行与并行打印
  def demo1(): Unit = {
    (1 to 10).foreach(println) //输出有序
    println("-------")
    (1 to 10).par.foreach(println) //输出无序
  }
}
