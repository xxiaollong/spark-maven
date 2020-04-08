package com.example.scala.demo3

/**
  * 继承 重写方法
  *
  * 案例：存取收取1元手续费
  */
object OverrideDemo01 {
  def main(args: Array[String]): Unit = {
    val o1 = new Ove02(100)
    o1.show()
    o1.add(100)
    o1.show()
    o1.less(100)
    o1.show()
  }

}

class Ove01(inSum: Int) {
  private var sum:Int = inSum

  def add(num: Int) = sum += num
  def less(num: Int) = sum -= num
  def show() = println(s"当前余额为：$sum")
}

class Ove02(inSum: Int) extends Ove01(inSum: Int){

  override def add(num: Int): Unit = super.add(num - 1)
  override def less(num: Int): Unit = super.less(num + 1)
}
