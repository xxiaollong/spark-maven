package com.example.scala.demo4

/**
  * 枚举练习
  */
object EnumerateDemo01 {
  def main(args: Array[String]): Unit = {
    println(ABCPlay.toString())
    println(ABCPlay.isBig(ABCPlay.A))
    println(ABCPlay.isBig(ABCPlay.B))
  }
}

object ABCPlay extends Enumeration {
  // 给Value类型去别名
  type ABCPlay = Value
  val A = Value("A")
  val B = Value("b")
  val C = Value("C")
  val D = Value("d")

  // 重新toString方法
  override def toString(): String = {
    ABCPlay.values.mkString(",")
  }

  // 判断大小写
  def isBig(c: ABCPlay) = c == A || c == C

}
