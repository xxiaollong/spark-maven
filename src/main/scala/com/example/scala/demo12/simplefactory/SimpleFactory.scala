package com.example.scala.demo12.simplefactory

/**
  * 简单工厂模式
  *
  * 工厂类
  */
object SimpleFactory {

  def createPiazz(id: Int): Pizza = {

    var pazz: Pizza = null

    id match {
      case 1 => pazz = new GreekPizza
      case 2 => pazz = new PepperPizza
      case 3 => pazz = new CheesePizza
      case _ =>
    }

    pazz
  }

}
