package com.example.scala.demo3

/**
  * 方法重写
  *
  * 属性重写
  *   1、只能对val的属性进行重载
  *   2、重写是通过该属性对应的方法实现的
  */
object MethodOverrideDemo01 {
  def main(args: Array[String]): Unit = {
    val e:Persion = new Emp
    e.name = "tom"
    e.showName()
    println(e.age)

    val e2:Emp = new Emp
    e2.name = "cat"
    e2.showName()
  }
}

class Persion{
  var name:String = ""
  val age = 10

  def showName(): Unit ={
    println("Persion name = " + this.name)
  }
}

class Emp extends Persion{

  // 属性重写
  override val age: Int = 100

  // 重写父类方法
  override def showName(): Unit = {
    println("Emp name = " + this.name)
    // 子类中调用父类方法使用super
    super.showName()
  }
}


