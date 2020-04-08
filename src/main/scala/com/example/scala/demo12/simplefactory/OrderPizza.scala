package com.example.scala.demo12.simplefactory

import scala.io.StdIn

/**
  * 简单工厂模式 + 工厂方法模式
  *
  * 创建披萨
  */
object OrderPizza {

  def main(args: Array[String]): Unit = {
    //    demo1()
    //    demo2()
    demo3()
  }

  // 工厂方法模式
  def demo3(): Unit = {
    var flag: Boolean = true
    var orderType = ""
    var piazz: Pizza = null

    while (flag) {
      println("请输入披散名称：1-希腊，2-胡椒，3-奶酪")
      orderType = StdIn.readLine()
      orderType match {
        case "1" => piazz = GreekFactoryMethod.createPiazz(1)
        case "3" => piazz = CheeseFactoryMethod.createPiazz(3)
        case _ => piazz = null
      }
      if (piazz == null) {
        flag = false
      } else {
        piazz.prepare()
        piazz.bake()
        piazz.cut()
        piazz.box()
      }
    }
  }

  // 简单工厂模式
  def demo2(): Unit = {
    var orderType = 0
    var flag: Boolean = true

    while (flag) {
      println("请输入披散名称：1-希腊，2-胡椒，3-奶酪")
      orderType = StdIn.readInt()
      val piazz: Pizza = SimpleFactory.createPiazz(orderType)
      if (piazz == null) {
        flag = false
      } else {
        piazz.prepare()
        piazz.bake()
        piazz.cut()
        piazz.box()
      }
    }
  }


  // 传统方式
  def demo1(): Unit = {
    var orderType: String = ""
    var pazz: Pizza = null
    var flag: Boolean = true

    while (flag) {
      println("请输入披散名称：1-希腊，2-胡椒，3-奶酪")
      orderType = StdIn.readLine()
      if ("1".equals(orderType)) {
        pazz = new GreekPizza
      } else if ("2".equals(orderType)) {
        pazz = new PepperPizza
      } else if ("3".equals(orderType)) {
        pazz = new CheesePizza
      } else {
        flag = false
      }

      pazz.prepare()
      pazz.cut()
      pazz.bake()
      pazz.box()
    }

  }

}
