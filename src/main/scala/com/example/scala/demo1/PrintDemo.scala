package com.example.scala.demo1

/**
  * 输出字符串的三种方式
  */
object PrintDemo {

  def main(args: Array[String]): Unit = {
    val str1: String = "Hello"
    val str2: String = " World"

    // 使用+连接
    println(str1 + str2)

    val name: String = "Tom"
    val age: Int = 10
    val sal: Float = 1024.123f
    val hg: Double = 123.123


    // 使用printf+%格式换输出
    printf("姓名：%s，年龄：%d，薪水：%.2f，身高：%.2f \n", name, age, sal, hg)

    // 使用$做变量替换
    println(s"姓名：$name, 年龄：$age")
    println(s"姓名：$name, 年龄：${age + 10}")


    // 判断变量类型
    println(age.isInstanceOf[Int])

    val c1: Char = 'a'
    val num1: Int = c1 + 10

    println("num1 = " + num1)

  }
}
