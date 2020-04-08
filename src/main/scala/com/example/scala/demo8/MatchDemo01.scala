package com.example.scala.demo8

/**
  * match 匹配模式
  */
object MatchDemo01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    //    demo2()
    //    demo3()
    //    demo4()
    //    demo5()
    //    demo6()
    //    demo7()
    //    demo8()
    //    demo9()
    demo10()

  }

  // 模式匹配--for循环中的模式匹配
  def demo10(): Unit = {
    val map = Map("AA" -> 1, "BB" -> 2, "CC" -> 3)

    for ((k, v) <- map) {
      println(s"($k, $v)")
    }
    println("------------")

    // 表示只过滤出v=1的元素
    for ((k, 1) <- map) {
      println(s"($k, 1)")
    }
    println("------------")

    // 使用循环守卫，可以写一个表达式，更加灵活强大
    for ((k, v) <- map; if v == 1 || v == 2) {
      println(s"($k, $v)")
    }

  }


  // 模式匹配--遍历声明中的模式匹配
  def demo9(): Unit = {
    val (x, y) = (10, true)
    println(s"x = $x, y = $y")

    val (q, r) = BigInt(10) /% 3
    println(s"q = $q, r = $r")

    val arr = Array(10, 20, 30)
    val Array(f, s, _) = arr
    println(s"f = $f, s = $s")

  }


  // 模式匹配--匹配对象
  def demo8(): Unit = {
    val num: Double = 36.0

    num match {
      // 触发对象提取器
      case Square(n) => println("匹配成功，n = " + n)
      case _ => println("没有匹配到对应的对象")
    }

    val num1: Double = Square(5.0)

    num1 match {
      case Square(n) => println("匹配成功，n = " + n)
      case _ => println("没有匹配到对应的对象")
    }

  }


  // 模式匹配--匹配元组
  def demo7(): Unit = {
    val tuples = ((), (1, 2), (1, 2, 3), (2, 3), ("aa", 10, false))

    for (t <- tuples.productIterator) {
      println(t)
      val res = t match {
        case () => "匹配到空元组"
        case (1, _) => "匹配到了以1开头且只包含两个元素的元组"
        case (x, 3) => "匹配到了两个元素的元组，且第二个元素为3"
        case (x, y, z) if z == 3 => (z, y, x)
        case _ => "没有匹配到对应的元组"
      }

      println(res)
      println("-------------")
    }


  }


  // 模式匹配--匹配列表
  def demo6(): Unit = {
    val lists = List(List(), List(1, 2), List(true), List(88), List("aa", 2, true), List(false))
    for (list <- lists) {
      println(list)
      val res = list match {
        case Nil => "匹配到空列表"
        case true :: Nil => "匹配到：List(true)"
        case x :: y :: Nil => "匹配到两个元素的列表"
        case "aa" :: tail => "匹配到以\"aa\"开头的列表"
        case x :: Nil if x == 88 => x
        case _ => "没有匹配到对应的列表"
      }

      println(res)
      println("-----------")
    }

  }


  // 模式匹配--匹配数组
  def demo5(): Unit = {
    val arrs = Array(Array(0), Array(0, 0, 0), Array(0, 1), Array(1, 0), Array(1, 1), Array(1, 2, 3), Array(0, 2, 3))
    for (arr <- arrs) {
      print(arr.toBuffer + "\t")
      val res = arr match {

        case Array(0) => "元素只有1个0的数组"
        case Array(1, _) => "元素以1开头只包含2个元素的数组"
        case Array(x, y) => s"Array($x, $y)"
        case Array(0, _*) => "元素以0开头包含多个元素的数组"
        case _ => "没有匹配到对应的集合"

      }
      println(res)
    }

  }


  // 模式匹配--类型匹配
  def demo4(): Unit = {
    val n = 4
    val obj1 = n match {
      case 1 => 1 :: 2 :: Nil
      case 2 => true
      case 3 => Map(11 -> "AA")
      case _ => "Hello"
    }

    val res = obj1 match {
      case a: Int => a + " 是Int类型"
      case b: Boolean => b + " 是Boolean类型"
      case c: List[Int] => c + " 是List[Int]类型"
      case d: Map[Int, String] => d + " 是[Int, String]类型"
      // 这种写法隐藏变量名，只关心类型，不是默认匹配
      case _: BigInt => " 是BigInt类型"
      // 默认匹配
      case _ => "obj 不知道什么类型"
    }

    println("res = " + res)

  }


  // 模式匹配返回值
  def demo3(): Unit = {
    val op = '+'

    val res = op match {
      case '+' => "返回值为：+"
      case '-' => "返回值为：+"
      case _ => "没有匹配"
    }

    println("res = " + res)
  }


  // match守卫
  def demo2(): Unit = {
    val list = List(1, 2, 3, 4, 5)
    for (n <- list) {

      n match {
        case _ if n <= 3 => println(s"n = $n, 小于等于3")
        case _ => println(s"n = $n, 大于3")

      }

    }
  }


  // 相当于Java的switch，但是功能更加强大
  def demo1(): Unit = {
    val n1 = 10
    val n2 = 20
    var res = 0

    val op = "&"

    op match {
      case "-" => res = n1 - n2
      case "+" => res = n1 + n2
      case "*" => res = n1 * n2
      case "/" => res = n1 / n2
      case _ => println("操作符错误")
    }

    println("res = " + res)

  }

}

// 对象匹配
object Square {
  // 对象提取器
  // 返回Some集合则匹配成功
  // 返回None则匹配失败
  def unapply(z: Double): Option[Double] = {
    println("对象提取器被调用了，z = " + z)
    //    Some(math.sqrt(z))
    None
  }

  // 构造器
  def apply(z: Double): Double = z * z
}

