package com.example.scala.demo4

/**
  *
  * apply(): 在伴生对象中使用apply()方法实例化对象
  *
  */
object ApplyDemo01 {
  def main(args: Array[String]): Unit = {
    // 次数list对象是调用List的apply方法构造的
    val list = List(1, 2, 3)
    println(list)

    // 使用new关键字构造
    val p1:Pig = new Pig("常规猪")
    // 使用带参apply(name: String)构造
    val p2 = Pig("小花猪")
    // 使用无参apply()构造
    val p3 = Pig()

    println(p1.toString)
    println(p2.toString)
    println(p3.toString)

  }
}

class Pig(inName:String){
  val name:String = inName

  override def toString = s"Pig($name)"
}

object Pig{

  def apply(name: String): Pig = new Pig(name)
  // 无参时注意apply()方法的小括号
  def apply(): Pig = new Pig("匿名猪")
}
