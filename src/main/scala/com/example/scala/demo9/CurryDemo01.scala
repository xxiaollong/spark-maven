package com.example.scala.demo9

/**
  *
  * 函数柯里化
  *
  * 一个函数对应一个参数
  *
  */
object CurryDemo01 {
  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()
  }


  // 柯里化案例--两个字符串忽略大小写，比较是否相等
  def demo2(): Unit = {
    // 传统写法
    def eqStr1(s1: String, s2: String): Boolean = {
      s1.toUpperCase == s2.toUpperCase
    }
    println(eqStr1("hello", "HELLO"))

    // 柯里化写法
    // 定一个个比较是否相等的函数
    def myEq(s1:String, s2:String): Boolean ={
      s1 == s2
    }

    // 定义了一个隐式类，String类型的变量可以直接调用其中的方法
    implicit class EqTest(s: String){

      // 隐式类中的方法
      // (s1: String) 表示传入需要比较的字符串
      // f:(String, String)=>Boolean 表示执行比较的方法
      // 传入的每一个参数只做一件事情
      def checkEq(s1: String)(f:(String, String)=>Boolean): Boolean = {
        f(s.toUpperCase, s1.toUpperCase)
      }
    }

    val s1:String = "hello"
    val s2:String = "HELLO-"

    println(s1.checkEq(s2)(myEq))

    // 柯里化的简单写法
    println(s1.checkEq(s2)((s1:String, s2:String) => s1.equals(s2)))
    println(s1.checkEq(s2)((s1, s2) => s1.equals(s2)))
    println(s1.checkEq(s2)(_.equals(_)))


  }

  // 函数柯里化案例
  // 传入两个只求和
  def demo1(): Unit = {

    // 传统写法
    def sum1(n1: Int, n2: Int): Int = n1 + n2
    println(sum1(10, 20))

    // 闭包写法
    def sum2(n1: Int) = {
      (n2: Int) => n1 + n2
    }
    println(sum2(10)(20))

    // 柯里化写法
    def sum3(n1: Int)(n2: Int) = n1 + n2
    println(sum3(10)(20))


  }

}
