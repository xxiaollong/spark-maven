package com.example.scala.demo3

import scala.beans.BeanProperty

/**
  * BeanProperty
  *
  * 主构造器参数可见性及性质
  */
object BeanPropertyDemo01 {
  def main(args: Array[String]): Unit = {
    val car: Car01 = new Car01
    car.name = "tom"
    println(car.name)

    car.setName("hah")
    println(car.getName)

    //
    car2_4()
  }

  def car2_4(): Unit ={
    val car2: Car02 = new Car02("22")
    car2.show()
    println("-----------")

    val car3: Car03 = new Car03("33")
    println(car3.inName)
    car3.inName = "3333"
    car3.show()
    println("-----------")

    val car4: Car04 = new Car04("44")
    println(car4.inName)
    // car4.inName = "4444"  报错
    car4.show()
  }

}

class Car01(){
  @BeanProperty var name:String = _
}

// 主构造器参数：形参，内部使用，外部无法使用
class Car02(inName:String){
  var name:String = inName

  def show(): Unit ={
    println("Car02-" + inName)
  }
}

// 主构造器参数：属性，内外都可使用，可读写
class Car03(var inName:String){
  var name:String = inName

  def show(): Unit ={
    println("Car03-" + inName)
  }
}

// 主构造器参数：属性，内外都可使用，只读
class Car04(val inName:String){
  var name:String = inName

  def show(): Unit ={
    println("Car04-" + inName)
  }
}
