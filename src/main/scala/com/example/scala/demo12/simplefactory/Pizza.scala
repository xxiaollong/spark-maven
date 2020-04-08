package com.example.scala.demo12.simplefactory

/**
  * 简单工厂模式
  *
  * 各种产品
  *
  */

// 披萨的父类
abstract class Pizza {
  var name: String = _

  //假定，每种pizza 的准备原材料不同，因此做成抽象的..
  def prepare() //抽象方法

  def cut(): Unit = {
    println(this.name + " cutting ..")
  }

  def bake(): Unit = {
    println(this.name + " baking ..")
  }

  def box(): Unit = {
    println(this.name + " boxing ..")
  }
}

// 胡椒披萨
class PepperPizza extends Pizza{
  override def prepare(): Unit = {
    this.name = "胡椒pizza"
    println(this.name + " preparing")
  }
}

// 奶酪披萨
class CheesePizza extends Pizza{
  override def prepare(): Unit = {
    this.name = "奶酪pizza"
    println(this.name + " preparing")
  }
}

// 希腊披萨
class GreekPizza extends Pizza {
  override def prepare(): Unit = {
    this.name = "希腊pizza"
    println(this.name + " preparing")
  }
}

