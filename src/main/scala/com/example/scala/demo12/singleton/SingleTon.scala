package com.example.scala.demo12.singleton

/**
  * 单例模式--懒汉式
  *
  *
  */
object Test{
  def main(args: Array[String]): Unit = {
    val o1 = SingleTon.getObj
    val o2 = SingleTon.getObj

    if (o1 == o2){
      println("同一个对象")
    }
  }
}


class SingleTon private() {

}

object SingleTon{
  private var o: SingleTon = null
  def getObj: SingleTon ={
    if (o == null){
      o = new SingleTon()
    }
    o
  }
}