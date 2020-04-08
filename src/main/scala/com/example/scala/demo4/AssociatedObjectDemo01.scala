package com.example.scala.demo4

/**
  * 伴生对象
  * 伴生类
  *
  * 小孩子加入玩游戏案例
  *
  */
object AssociatedObjectDemo01 {
  def main(args: Array[String]): Unit = {
    //    scalaPersonTest()
    childGamesTest()
  }

  def childGamesTest(): Unit ={
    val c1 = new ChildGames("小狐狸")
    ChildGames.joinGame(c1)
    ChildGames.showCount()
    val c2 = new ChildGames("小狗熊")
    ChildGames.joinGame(c1)
    ChildGames.showCount()
    val c3 = new ChildGames("小呆瓜")
    ChildGames.joinGame(c1)
    ChildGames.showCount()
  }

  def scalaPersonTest(): Unit = {
    // 实际调用：ScalaPerson..MODULE$.flag();
    println(ScalaPerson.flag)
    // 实际调用：ScalaPerson..MODULE$.say();
    ScalaPerson.say()
  }
}

class ChildGames(inName:String){
  val name:String = inName
}

object ChildGames{
  var count: Int = 0

  def joinGame(c: ChildGames): Unit ={
    println(c.name + " 加入游戏")
    count += 1
  }

  def showCount(): Unit ={
    println(s"共有 $count 个小孩子在玩游戏")
  }
}


// 是ScalaPerson的伴生类
// 编译后生成ScalaPerson.class文件
class ScalaPerson {

}

// 是ScalaPerson的伴生对象
// 编译后生成ScalaPerson$.class文件
// 如果没有声明对应的伴生类，则会生成对应的伴生类
object ScalaPerson {

  val flag: Boolean = true

  def say(): Unit = {
    println("Hi..")
  }

}
