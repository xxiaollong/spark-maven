package com.example.scala.demo9

/**
  * 闭包
  *
  * 定义：闭包是一个函数与其相关的引用环境组合的一个整体
  *
  *
  */
object ClosureDemo01 {
  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()

  }

  // 闭包案例--补齐文件名
  def demo2(): Unit = {

    def fileName(suffix: String) = {
      // 以下匿名函数+suffix: String 形成了1个闭包
      (name: String) => {
        if (name.endsWith(suffix)) {
          name
        } else {
          name + suffix
        }
      }
    }

    // f1就绑定了一个规则，方便后续持续调用
    val suffix = ".jpg"
    val f1 = fileName(suffix)

    println(f1("hello"))
    println(f1("hello.jpg"))

  }


  // 闭包简介
  def demo1(): Unit = {
    def f1(n1: Int) = {
      // 返回的函数为：f(n2: Int) => n1 - n2
      // 该函数引用了外部传入的n1, 该函数与n1整体形成了一个闭包
      (n2: Int) => n1 - n2
    }

    val res1 = f1(10)(3)
    println("res1 = " + res1)


    // n1在返回函数中已经绑定对应的值
    var n1 = 10
    val res2 = f1(n1)
    val res3 = f1(n1)
    val res2_1 = res2(3)
    println("res2_1 = " + res2_1)
    n1 = 20
    val res3_1 = res3(3)
    println("res3_1 = " + res3_1)

  }
}
