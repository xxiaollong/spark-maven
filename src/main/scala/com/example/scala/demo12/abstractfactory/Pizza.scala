package com.example.scala.demo12.abstractfactory

/**
  * 抽象工厂模式
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

// BJ-胡椒披萨
class BJPepperPizza extends Pizza{
  override def prepare(): Unit = {
    this.name = "BJ-胡椒pizza"
    println(this.name + " preparing")
  }
}

// BJ-奶酪披萨
class BJCheesePizza extends Pizza{
  override def prepare(): Unit = {
    this.name = "BJ-奶酪pizza"
    println(this.name + " preparing")
  }
}

// BJ-希腊披萨
class BJGreekPizza extends Pizza {
  override def prepare(): Unit = {
    this.name = "BJ-希腊pizza"
    println(this.name + " preparing")
  }
}

// SH-胡椒披萨
class SHPepperPizza extends Pizza{
  override def prepare(): Unit = {
    this.name = "SH-胡椒pizza"
    println(this.name + " preparing")
  }
}

// SH-奶酪披萨
class SHCheesePizza extends Pizza{
  override def prepare(): Unit = {
    this.name = "SH-奶酪pizza"
    println(this.name + " preparing")
  }
}

// SH-希腊披萨
class SHGreekPizza extends Pizza {
  override def prepare(): Unit = {
    this.name = "SH-希腊pizza"
    println(this.name + " preparing")
  }
}

