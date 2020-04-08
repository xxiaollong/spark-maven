package com.example.scala.demo12.simplefactory

import scala.io.StdIn

/**
  * 工厂方法模式
  *
  *
  */
// 该工厂提供了创建对象的方法，该方法由使用者自己实现
abstract class FactoryMethod {
  def getPiazz(id: Int): Pizza ={
    val piazz: Pizza = createPiazz(id)
    piazz
  }

  def createPiazz(id: Int): Pizza
}

// 使用者自己实现创建方法
object GreekFactoryMethod extends FactoryMethod{
  override def createPiazz(id: Int): Pizza = {
    new GreekPizza
  }
}

// 使用者自己实现创建方法
object CheeseFactoryMethod extends FactoryMethod{
  override def createPiazz(id: Int): Pizza = {
    new CheesePizza
  }
}
