package com.example.scala.demo3

/**
  * 构造器
  */
object ConstructorDemo01 {
  def main(args: Array[String]): Unit = {
    //    val p1_1 = new Persion01("tom", 20)
    //    println(p1_1.toString)
    //    // 重载主构造器为带参构造器后，一下写法错误
    //    // val p1_2 = new Persion01()
    //
    //
    //    // 无参构造器的两种写法
    //    val p2_1 = new Persion02
    //    val p2_2 = new Persion02()

    /*
     *执行顺序
     *AA()-主构造器
     *BB()-主构造器
     *BB-this(name:String)-辅构造器
     */
    val b = new BB("tom")

  }

}

// 主构造器
class Persion01(inName: String,
                inAge: Int) {

  var name: String = inName
  var age: Int = inAge

  override def toString = s"Persion01($name, $age)"

}

// 默认主构造器：无参
class Persion02() {
  var name: String = _
  var age: Int = _

}

class AA() {
  println("AA()-主构造器")
}
/*
 *执行顺序
 *AA()-主构造器
 *BB()-主构造器
 *BB-this(name:String)-辅构造器
 */
class BB() extends AA() {
  println("BB()-主构造器")

  def this(name: String) {
    // 辅构造器中必须显式调用主构造器
    this()
    println("BB-this(name:String)-辅构造器")

  }
}
