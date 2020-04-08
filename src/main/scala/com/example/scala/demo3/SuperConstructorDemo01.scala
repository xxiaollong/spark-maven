package com.example.scala.demo3

/**
  * 超类构造器执行流程分析
  *
  * 注意：只有子类的主构造器才能调用父类的主构造器
  */
object SuperConstructorDemo01 {
  def main(args: Array[String]): Unit = {

    //    test1()
    test2()
  }
  // 超类主构造器为带参构造器
  def test2(): Unit = {
    // 主构造器执行流程分析
    // 1、Father1
    // 2、Father1 this(name: String)
    // 3、Child1
    val c1 = new Child1

    println("----------")

    // 辅助构造器执行流程分析
    // 1、Father1
    // 2、Father1 this(name: String)
    // 3、Child1
    // 4、Child1 this(name: String)
    val c2 = new Child1("tom")

    println(c2.name)
  }

  // 超类主构造器为默认构造器
  def test1(): Unit = {
    // 主构造器执行流程分析
    // 1、Father
    // 2、Child
    val c1 = new Child

    println("----------")

    // 辅助构造器执行流程分析
    // 1、Father
    // 2、Child
    // 3、Child this(name: String)
    val c2 = new Child("tom")
  }
}

class Father1(inName: String) {
  var name = inName
  println("Father1")

  def this() {
    // 调用本身的主构造器
    this("")
    println("Father1 this(name: String)")
  }
}

class Child1 extends Father1 {
  println("Child1")

  def this(name: String) {
    // 调用本身的主构造器
    this
    this.name = name
    println("Child1 this(name: String)")
  }
}

class Father {
  var name = ""
  println("Father")

  def this(name: String) {
    // 调用本身的主构造器
    this
    this.name = name
    println("Father this(name: String)")
  }
}

class Child extends Father {
  println("Child")

  def this(name: String) {
    // 调用本身的主构造器
    this
    this.name = name
    println("Child this(name: String)")
  }
}
