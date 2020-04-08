package com.example.scala.demo7

/**
  * 视图
  *     产生懒加载效果
  */
object ViewDemo01 {
  def main(args: Array[String]): Unit = {
    demo1()
  }

  // 过滤出1-100以内反转之后依然值自己的数值
  def demo1(){
    val res1 = (1 to 100).filter(eq)
    println("res1 = " + res1)

    val res2 = (1 to 100).view.filter(eq)
    println("res2 = " + res2)

    for (item <- res2){
      println(item)
    }


  }

  def eq(n: Int): Boolean ={
    n == n.toString.reverse.toInt
  }

}
