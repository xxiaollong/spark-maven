package com.example.scala.demo2

/**
  * 方法和函数
  */
object MethedDemo01 {
  def main(args: Array[String]): Unit = {
    val w = 10.13
    val h = 10.55

    val r1 = getArea(w, h)
    println("r1 = " + r1)

    val r2 = new Rectangle
    r2.width = w
    r2.height = h
    println("r2 = " + r2.getArea)

    println(oddOreven(4))

  }

  // 判断奇数或偶数
  def oddOreven(n: Int): String = {
    if (n == 0) {
      "非奇非偶"
    } else if (n % 2 == 0) {
      "偶数"
    } else {
      "奇数"
    }

  }

  // 与类的属性无关，接收外部参数或不接受参数，即为函数
  def getArea(w: Double, h: Double): Double = {
    (w * h).formatted("%.2f").toDouble
  }

}

// 类中定义的和属性相关，即为方法
class Rectangle {
  var width: Double = 0.0
  var height: Double = 0.0

  def getArea: Double = {
    (this.width * this.height).formatted("%.2f").toDouble
  }

}

