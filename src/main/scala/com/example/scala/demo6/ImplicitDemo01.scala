package com.example.scala.demo6

/**
  * 隐式转换
  *
  * 隐式值优先级
  *
  */
object ImplicitDemo01 {
  def main(args: Array[String]): Unit = {
    //    demo01()
    //    demo02()
    //    demo03()
    demo04()
  }

  // 优先级：传值 > 隐式值 > 默认值
  // 隐式值不能出现二义性，否则报错
  // 如果传值，隐式值、默认值都没有，会报错
  def demo04(): Unit = {

    // 报错,出现二义性
    def s1(): Unit = {
      // 编译时报错
      //      implicit val name: String = "tom1"
      implicit val name2: String = "tom2"

      def hello(implicit name: String): Unit = {
        println("hello " + name)
      }

      hello
    }
    s1()

    // 正确 输出声明的隐式值
    def s2(): Unit = {
      implicit val name: String = "tom"

      def hello(implicit name: String = "aaa"): Unit = {
        println("hello " + name)
      }

      hello
    }
    s2()

    // 正确 输出形参默认值
    // 隐式值与形参类型部匹配，无关联
    def s3(): Unit = {
      implicit val age: Int = 20

      def hello(implicit name: String = "aaa"): Unit = {
        println("hello " + name)
      }

      hello
    }
    s3()

    // 错误，找不到形参的隐式值
    def s4(): Unit = {
      implicit val age: Int = 20
      // could not find implicit value for parameter name: String
      //      def hello(implicit name: String): Unit = {
      //        println("hello " + name)
      //      }

      //      hello
    }
    s4()

  }

  // 隐式值
  def demo03(): Unit = {
    // 声明一个隐式值
    implicit val str: String = "tom"

    // 隐式参数
    def hello(implicit s: String): Unit = {
      println(s + " hello")
    }

    hello
  }

  // 应用类型隐式转换示例
  def demo02(): Unit = {
    implicit def MySQL2DB(mysql: MySQL): DB = {
      new DB
    }

    val mysql = new MySQL
    mysql.insert(mysql)
    mysql.delete(mysql)
  }

  // 值类型隐式转换示例
  def demo01(): Unit = {
    // 定义隐式转换函数
    // 隐式含糊应当在对应的作用域才能生效
    implicit def doubel2int(d: Double): Int = {
      d.toInt
    }

    val num: Int = 3.5
    println("num = " + num)
  }
}

class MySQL() {
  def insert(obj: Any): Unit = {
    println(obj.getClass.getName + " insert() 执行了...")
  }
}

class DB() {
  def delete(obj: Any): Unit = {
    println(obj.getClass.getName + " delete() 执行了...")
  }
}