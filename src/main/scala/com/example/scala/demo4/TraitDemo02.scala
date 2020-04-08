package com.example.scala.demo4

/**
  * trait 的使用
  *
  */
object TraitDemo02 {
  def main(args: Array[String]): Unit = {
    val t1 = new Test001
    t1.say()
    t1.sayHi()
    t1.sayHello()
  }
}
// 既包含抽象属性，又包含普通属性，生成两个文件：T006.class、 T006$class.class
trait T006 {
  val name:String
  val age:Int = 50
}

// 只有抽象属性，生成一个文件：T005.class
trait T005 {
  val name:String
}

// 当只有抽象方法是，只生成一个文件：T003.class
trait T003 {
  def say()
}

// 当既有抽象方法，也有普通方法时，生成两个文件
// T004.class : 该文件为接口，其中声明了所有方法（public abstract interface T004）
// T004$class.class : 该文件中实现了普通方法（）
trait T004 {
  def sayHi()

  def sayHello(): Unit = {
    println("T003 sayHello")
  }
}

// 底层是：public class Test001 implements T003, T004
class Test001 extends T003 with T004 {
  // 编译后增加了trait中的普通方法，方便调用
  //  public void sayHello()
  //  {
  //    T004.class.sayHello(this);
  //  }

  // 实现trait的抽象方法
  override def say(): Unit = {
    println("Test001 say")
  }

  // 实现trait的抽象方法
  override def sayHi(): Unit = {
    println("Test001 sayHi")
  }
}