package com.example.scala.demo12.singleton

/**
  * 单例模式--饿汉式
  *
  *
  */
object Test2{
  def main(args: Array[String]): Unit = {
    val o1 = SingleTon2.getObj
    val o2 = SingleTon2.getObj

    if (o1 == o2){
      println("同一个对象")
    }
  }
}


class SingleTon2 private() {

}

object SingleTon2{
  private val o: SingleTon2 = new SingleTon2()
  def getObj: SingleTon2 ={
    o
  }
}