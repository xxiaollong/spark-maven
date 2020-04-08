package com.example.scala.demo9

/**
  * 偏函数
  *
  * 匿名函数
  *
  */
object PartialFunDemo01 {

  def main(args: Array[String]): Unit = {
    demo1()
  }

  // 偏函数
  // 对列表中的数字+1，形成一个新集合
  def demo1(): Unit = {
    val list = List(1, 2, 3, "hello")

    // 传统写法, 麻烦
    println(list.filter(f1).map(f2).map(f3))

    // 使用类型匹配,但是因为类型问题还是有点麻烦
    println(list.map(addOne).filter(f1))

    // 使用变函数解决
    // 定义一个偏函数
    // new PartialFunction[Any, Int]: 创建一个偏函数对象，入参数Any，出参数Int
    val partialFun = new PartialFunction[Any, Int] {
      // 该方法返回值为Bool类型，判断入参是否符合要求，返回true则调用apply()方法，返回false则忽略
      override def isDefinedAt(x: Any): Boolean = x.isInstanceOf[Int]

      // 该方法对入参进行逻辑处理
      override def apply(v1: Any): Int = {
        v1.asInstanceOf[Int] + 1
      }
    }

    // 使用偏函数
    // map()不支持偏函数，所以使用collect()
    val list2 = list.collect(partialFun)
    println("list2 : " + list2)



    // 简化写法
    // 偏函数简写--形式1
    def parFun: PartialFunction[Any, Int] = {
      case i: Int => i + 1
    }
    val list3 = list.collect(parFun)
    println("list3 : " + list3)

    // 偏函数简写--形式2
    val list4 = list.collect({ case i: Int => i + 1 })
    println("list4 : " + list4)

    // 偏函数简写--形式3
    val list5 = list.collect { case i: Int => i + 1 }
    println("list5 : " + list5)


    // 偏函数简写--形式3
    val list_1 = List(1, 2, 3, 5.4, "hello")
    val list6 = list_1.collect {
      case i: Int => i + 1
      case j: Double => (j * 2).toInt
    }
    println("list6 : " + list6)



  }

  // 使用模式匹配中的类型匹配
  def addOne(i: Any): Any = {
    i match {
      case x: Int => x + 1
      case _ => i
    }
  }

  // 判断元素是否为Int类型
  def f1(n: Any): Boolean = {
    n.isInstanceOf[Int]
  }

  // 类型转换
  def f2(n: Any): Int = {
    n.asInstanceOf[Int]
  }

  // +1操作
  def f3(n: Int): Int = {
    n + 1
  }

}
