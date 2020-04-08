package com.example.scala.demo12.abstractfactory

/**
  * 抽象工厂模式
  *
  * 抽象工厂
  */
trait AbsFactory {
  def createPiazz(id: String):Pizza
}

// BJ-子工厂-具体实现
class BJFactory extends AbsFactory{
  override def createPiazz(id: String): Pizza = {
    var pazz: Pizza = null

    id match {
      case "1" => pazz = new BJGreekPizza
      case "2" => pazz = new BJPepperPizza
      case "3" => pazz = new BJCheesePizza
      case _ =>
    }

    pazz
  }
}

// SH-子工厂-具体实现
class SHFactory extends AbsFactory{
  override def createPiazz(id: String): Pizza = {
    var pazz: Pizza = null

    id match {
      case "1" => pazz = new SHGreekPizza
      case "2" => pazz = new SHPepperPizza
      case "3" => pazz = new SHCheesePizza
      case _ =>
    }

    pazz
  }
}