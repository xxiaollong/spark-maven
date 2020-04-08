package com.example.scala.demo5

/**
  * 动态混入
  */
object DynamicMixingDemo01 {
  def main(args: Array[String]): Unit = {
    demo01()

  }

  // 动态混入演示
  def demo01(): Unit ={
    // 普通类动态混入
    val oracle = new Oracle with SQlFun01
    oracle.insert("普通类： oracle")

    // 抽象类动态混入
    val mysql = new MySQL with SQlFun01
    mysql.insert("抽象类： mysql")

    // 抽象类-抽象方法动态混入
    val redis = new Redis with SQlFun01 {
      override def select(): Unit = {
        println("抽象类中的抽象方法执行了...")
      }
    }
    redis.insert("抽象类-抽象方法： redis")
    redis.select()

  }

}


trait SQlFun01{
  def insert(str: String): Unit ={
    println(str + " insert data ok..")
  }
}

// 普通类
class Oracle{

}

// 抽象类
abstract class MySQL{

}

// 抽象类-抽象方法
abstract class Redis{
  def select()
}

