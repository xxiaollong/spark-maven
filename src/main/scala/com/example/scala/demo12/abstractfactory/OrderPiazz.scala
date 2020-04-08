package com.example.scala.demo12.abstractfactory

import scala.io.StdIn

/**
  * 抽象工厂方法
  *
  * 创建
  */
class OrderPiazz {
  var absFactory: AbsFactory = _

  def this(absFactory: AbsFactory) {
    //多态
    this

    var orderType: String = null
    var pizza: Pizza = null
    var flag: Boolean = true

    while (flag) {
      println("请输入披散名称：1-希腊，2-胡椒，3-奶酪")
      orderType = StdIn.readLine()
      //使用简单工厂模式来创建对象.
      pizza = absFactory.createPiazz(orderType)
      if (pizza == null) {
        flag = false
      } else {
        pizza.prepare()
        pizza.bake()
        pizza.cut()
        pizza.box()
      }
    }
  }

}

object OrderPiazz {
  def main(args: Array[String]): Unit = {
    new OrderPiazz(new BJFactory)
    new OrderPiazz(new SHFactory)
  }
}
