package com.example.scala.demo2

/**
  * 可变参数
  */
object ParametersDemo {
  def main(args: Array[String]): Unit = {
    println(sum(10))
    println(sum(10, 20 , 30))
    noParmeterDemo()
  }

  // 1、函数体只有1句，则可以省略{}
  // 2、函数没有形参，则可以省略()
  def noParmeterDemo(): Unit ={
    // 此处定义了一个函数，没有形参，返回值为：Hello
    def f1 = "Hello"
    println(f1)
  }

  // 求1个或多个数的和
  // 可变参数必须写在形参列表的最后
  def sum(n: Int, args: Int*): Int = {
    var sum = n
    for (item <- args){
      sum += item
    }

    sum
  }

}
