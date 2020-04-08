package com.example.scala.demo1

/**
  * 类型相关
  */
object TypeDemo {

  def main(args: Array[String]): Unit = {

    // Unit的值为：()
    val s1 = UnitDemo()
    println(s"s1 = $s1")

    // Null测试
    NullDemo()

  }

  // Unit等价于void，Unit的值为：()
  def UnitDemo(): Unit = {

  }

  // Null为类型，只有一个实例，值为：null，
  // null可以赋值给任意AnyRef（引用类型）类型的变量
  // 但是不能赋值给任意AnyVal（值类型）类型的变量
  def NullDemo(): Unit = {
    val dog: Dog = null

    // val num: Int = null   // 此处编译时报错

  }

  // Nothing类型是最底层类型，是任意类型的子类，兼容性强
  def Nothing(): Unit ={

  }


}

class Dog {

}
