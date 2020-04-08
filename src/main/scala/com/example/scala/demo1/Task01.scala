package com.example.scala.demo1

import scala.io.StdIn

/**
  * Created by admin on 2019-09-11.
  */
object Task01 {

  def main(args: Array[String]): Unit = {
    //    t1()
    //    t2()
    //    t3()
    //    t4()
    //    t5()
    //    t6()
    //    t7()
    //    t8()
    //    t9()
    t10()
  }

  // if-else有返回值
  def t10(): Unit = {
    val num = 20

    val result = if (num >= 20) {
      "num >= 20"
    } else {
      "num < 20"
    }

    println("result = " + result)
  }

  // 从控制台获取数据
  def t9(): Unit = {
    println("请输入姓名：")
    val name = StdIn.readLine()
    println("请输入年龄：")
    val age = StdIn.readInt()
    println("请输入薪水：")
    val sal = StdIn.readDouble()

    printf("用户信息为：姓名：%s，年龄：%d，薪水：%.2f", name, age, sal)
  }

  // 交互两个变量
  def t8(): Unit = {
    var a = 10
    var b = 20

    a = a + b
    b = a - b
    a = a - b
    printf("a=%d, b=%d", a, b)

  }

  // 根据摄氏温度算出华氏温度
  def t7(): Unit = {
    val hsNum = 232.5
    //摄氏温度=5/9*(华氏温度 - 100)
    println(s"华氏温度为：${hsNum}，对应的摄氏温度为：${(5.0 / 9 * (hsNum - 100)).formatted("%.2f")}")
  }


  // 加入好友97天放假，问：XX星期XX天
  def t6(): Unit = {
    val day: Int = 97
    println(s"加入还有 ${day} 天放假,则还有 ${day / 7} 星期 ${day % 7} 天")
  }

  // 取模运算
  def t5(): Unit = {
    println(10 % 3)
    println(-10 % 3)
    println(10 % -3)
    println(-10 % -3)
  }

  // 获取字符串首末字符
  def t4(): Unit = {
    val str: String = "hello world"

    // 方法一：使用数组方式
    val sf_1 = str(0)
    val sl_1 = str(str.length - 1)
    println(s"${str}的首字符是:$sf_1, 尾字符是$sl_1")

    //方法二：使用take()
    val sf_2 = str.take(1)
    val sl_2 = str.reverse.take(1)
    println(s"${str}的首字符是:$sf_2, 尾字符是$sl_2")

    //方法二：使用take()
    val sf_3 = str.take(1)
    val sl_3 = str.takeRight(1)
    println(s"${str}的首字符是:$sf_3, 尾字符是$sl_3")
  }

  // BigInt的使用
  def t3(): Unit = {
    val num = BigInt(2)
    println(s"2的1024次方 = ${num.pow(1024)}")
  }

  // 字符串和数字运算，结果为字符串拼接n次
  def t2(): Unit = {
    println("'hello * 3' = " + ("hello" * 3))
  }

  // sqrt():求平方根
  // pow():求n次方
  def t1(): Unit = {
    val r = math.pow(math.sqrt(3), 2)

    println(s"3开根号后求平方的值为：$r")

  }

}
