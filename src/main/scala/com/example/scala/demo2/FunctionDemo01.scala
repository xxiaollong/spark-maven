package com.example.scala.demo2

/**
  * 函数的基本语法和定义
  *
  *
  */
object FunctionDemo01 {
  def main(args: Array[String]): Unit = {
//    println(getRes1(12, 20, '-'))
//    println(getRes2(12, 20, '-'))
//    res3()
//    res4()
    println(res4_2())
  }

  // 使用递归处理res4
  def res4_2(str:String="Hello"): Long ={
    if (str.length == 1){
      return str.charAt(0).toLong
    }

    str.take(1).charAt(0).toLong * res4_2(str.drop(1))

  }

  def res4(): Unit ={
    val str = "Hello"
    var nums:Long = 1
    str.foreach(nums *= _.toLong)
    println(nums)

    println("------")
    // 等价于
    var nums2:Long = 1
    def ch(i:Char): Unit ={
      nums2 *= i.toLong
    }
    str.foreach(ch)
    println(nums2)
  }

  // foreach
  def res3(): Unit ={
    (1 to 3).reverse.foreach(println)

    println("-------")

    // 以上函数等价于
    def show(i:Int): Unit ={
      println(i)
    }
    (1 to 3).reverse.foreach(show)
  }

  // 有返回值，且返回值类型固定
  //  def getRes(n1:Int, n2:Int, oper:Char) :Int = {
  // 有返回值，但返回值类型未知，类型推导
  def getRes2(n1: Int, n2: Int, oper: Char) = {
    oper match {
      case '+' => n1 + n2
      case '-' => n1 - n2
      case '*' => n1 * n2
      case '/' => n1 / n2
      case _ => throw new Exception("符号错误")
    }
  }

  // 无返回值，等价于 :Unit
  def getRes1(n1: Int, n2: Int, oper: Char){
    printf("n1=%d, n2=%d, oper='%c' \r\n", n1, n2, oper)
  }

}
