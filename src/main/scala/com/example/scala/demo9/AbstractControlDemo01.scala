package com.example.scala.demo9

/**
  * 抽象控制
  *
  * 方法的参数是一个函数，该函数没有入参，也没有出参
  *
  *
  *
  */
object AbstractControlDemo01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()
  }

  // 抽象控制--while案例
  def demo2() {

    def myWhile(f1: => Boolean)(f2: => Unit): Unit = {
      if (f1) {
        f2
        // 递归调用
        myWhile(f1)(f2)
      }
    }

    var num = 10
    myWhile(num > 0) {
      println("num = " + num)
      num -= 1
    }


  }


  // 抽象控制的基本使用及简化写法
  def demo1(): Unit = {

    def f1(f: () => Unit): Unit = {
      new Thread {
        override def run(): Unit = {
          f()
        }
      }.start()
    }

    f1 {
      () => {
        println("f1开始执行")
        Thread.sleep(5000)
        println("f1执行完成")
      }
    }


    // 简化写法
    def f2(f: => Unit): Unit = {
      new Thread {
        override def run(): Unit = {
          f
        }
      }.start()
    }

    f2 {
      println("f2开始执行")
      Thread.sleep(5000)
      println("f2执行完成")
    }


  }

}
