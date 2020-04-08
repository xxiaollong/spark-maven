package com.example.scala.demo3

/**
  * 类型判断和转换
  *
  * 类型转换的Demo
  */
object TypeCoverDemo01 {
  def main(args: Array[String]): Unit = {
    //    classOfDemo()
    //    isDemo()
    //    asDemo()

    cover01_1()


  }

  def cover01_1(): Unit = {
    val p = new Persion06
    p.id = 10

    val s = new Student06
    s.id = 20

    val e = new Emp06
    e.id = 30

    cover01(p)
    cover01(s)
    cover01(e)

  }

  // 类型装换demo
  def cover01(p: Persion06): Unit = {
    if (p.isInstanceOf[Student06]) {
      p.asInstanceOf[Student06].showId()
    } else if (p.isInstanceOf[Emp06]) {
      p.asInstanceOf[Emp06].showId()
    } else {
      p.showId()
    }
  }


  // 判断类型
  def isDemo(): Unit = {
    val p = new Persion
    println(p.isInstanceOf[Persion])
  }

  // 向下转换类型
  def asDemo(): Unit = {
    var p = new Persion
    var e = new Emp

    // 将子类赋给父类，自动向上转型
    p = e

    // 将父类赋给子类, 父类本质上是子类
    e = p.asInstanceOf[Emp]
  }

  // classOf[] 得到类名
  def classOfDemo(): Unit = {
    // 得到类名
    println(classOf[String])
    val str = "hello"
    // 通过反射获取类名
    println(str.getClass.getName)
  }
}

class Persion06 {
  var id = 0

  def showId(): Unit = {
    println("Persion04 id = " + this.id)
  }
}

class Student06 extends Persion06 {
  override def showId(): Unit = {
    println("Student06 id = " + this.id)
  }
}

class Emp06 extends Persion06 {
  override def showId(): Unit = {
    println("Emp06 id = " + this.id)
  }
}




