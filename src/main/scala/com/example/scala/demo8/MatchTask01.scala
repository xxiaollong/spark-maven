package com.example.scala.demo8

/**
  * 嵌套模式匹配
  *
  * 商品打折案例
  *
  */
object MatchTask01 {
  def main(args: Array[String]): Unit = {
    demo1()
  }

  def demo1(): Unit ={
    // 打折案例
    val sale = Bundle("书籍", 10, Book("海贼王", 40), Bundle("文学作品", 20, Book("阳关", 80), Book("围城", 30)))

    // 匹配出海贼王
    val res1 = sale match {
      case Bundle(_,_,Book(des,_),_*) => des
    }
    println("res1 = " + res1)

    // 使用@将匹配到的值绑定到变量中,_*标示绑定剩余的所有
    val res2 = sale match {
      case Bundle(_,_, x @ Book(_,_), xs @ _*) => (x, xs)
    }
    println("res2 = " + res2)

    val res3 = totalPrice(sale)
    println("总价为： " + res3)

  }

  // 获取总价
  def totalPrice(item :Item): Double ={
    item match {
      case Book(_, price) => price
      case Bundle(_, dis, its @ _*) => its.map(totalPrice).sum - dis
    }
  }

}

// 父类，子类可以以父类的方式统一传递
// 使用sealed修饰的类成为：密封类，本身及子类只能在当前文件中使用
abstract sealed class Item

//单本书：des: 描述, price:价格
case class Book(des: String, price: Double) extends Item

//商品套餐：des:描述, dis: 折扣金额, items: 商品集合
case class Bundle(des:String, dis: Double, items: Item*) extends Item