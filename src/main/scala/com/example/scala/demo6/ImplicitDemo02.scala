package com.example.scala.demo6

/**
  * 隐式类
  *
  *
  */
object ImplicitDemo02 {
  def main(args: Array[String]): Unit = {
     test1()

  }

  def test1(): Unit ={
    implicit class f1(val i: ImDemo1){
      def showBuffer(str: String): Unit ={
        println("f1 " + str)
      }
    }

    val i1 = new ImDemo1
    i1.show("haha")
    i1.showBuffer("aaa")
  }
}

class ImDemo1(){
  def show(str: String): Unit ={
    println("ImDemo1 " + str)
  }
}
