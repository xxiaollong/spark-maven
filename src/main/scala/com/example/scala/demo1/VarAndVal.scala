package com.example.scala.demo1

/**
  * var 和 val 的使用
  *
  * 建议：尽可能的使用val声明变量
  */
object VarAndVal {

  def main(args: Array[String]): Unit = {
    /*
    * var 声明变量，可以改变
    * val 声明常量，不可改变
    */

    val s1 = new Student()
    // 当一个对象的属性被声明为var时，可以改变
    s1.age = 10
    s1.name = "Tom"
    // 当一个对象的属性被声明为val时，不可改变
    //  s1.id = 101

    println(s1.toString)

  }

}

class Student {
  val id: Int = 100
  var name: String = ""
  var age: Int = 0

  override def toString = s"Student($id, $name, $age)"
}
