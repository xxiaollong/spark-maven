package com.example.scala.demo7

/**
  * 集合常用操作
  * map
  * flatMap
  * filter
  * reduce
  * fold
  * scan
  * zip
  */
object AllSetDemo01 {
  def main(args: Array[String]): Unit = {
    //    demo1()
    demo2()
    //    demo3()
    //    demo4()
    //    demo5()
    //    demo6()
    //    demo7()
  }

  // zip函数: 拉链（合并）
  def demo7(): Unit = {
    // 两个List必须是对偶元组，否则会造成数据丢失
    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)

    // 结果是一个元素为元组类型的List
    val res = list1.zip(list2)
    println("res = " + res)
  }


  // scan函数:
  def demo6(): Unit = {
    scan1()
  }

  def scan1(): Unit = {
    val list = List(1, 2, 3, 4)
    val res = list.scanLeft(5)(dif)
    println("res = " + res)


    println("---------------")

    val res2 = list.scanRight(5)(dif)
    println("res2 = " + res2)

  }

  // fold函数
  def demo5(): Unit = {
    fold1()
  }

  def fold1(): Unit = {
    val list = List(1, 2, 3, 4)
    val res = list.foldLeft(5)(dif)
    println("res = " + res)
    // foldLeft的简写方式
    val res_1 = (5 /: list) (dif)
    println("res_1 = " + res_1)

    println("---------------")

    val res2 = list.foldRight(5)(dif)
    println("res2 = " + res2)
    // foldRight的简写方式
    val res2_1 = (list :\ 5) (dif)
    println("res2_1 = " + res2_1)
  }


  // reduce函数: 化简
  def demo4(): Unit = {
    reduce1()
    println("===============")
    reduce2()

  }

  // 获取最小值
  def reduce2(): Unit = {
    val list = List(12, 5, 87, 4, 56, 4, 10)
    val res = list.reduce((n1, n2) => {
      if (n1 >= n2) {
        n2
      } else {
        n1
      }
    })
    println("res = " + res)

    println("--------------")
    val res2 = list.reduce(min)
    println("res2 = " + res2)

  }

  def reduce1(): Unit = {
    val list = List(10, 20, 30, 40)
    val res = list.reduce(_ + _)
    println("res = " + res)

    println("------------------")

    val res2 = list.reduce(sum)
    println("res2 = " + res2)

    println("------------------")

    val res3 = list.sum
    println("res3 = " + res3)

    println("------------------")
    val res4 = list.reduceRight(sum)
    println("res4 = " + res4)

  }


  // filter函数：过滤
  def demo3(): Unit = {
    filter1()
  }

  def filter1() {
    val list1 = List("AA", "BB", "ABC")
    val res = list1.filter(_.startsWith("A"))
    println("res = " + res)

    println("--------------")

    val list2 = List("AA", "BB", "abc")
    val res2 = list2.filter(isStartWithU)
    println("res2 = " + res2)
  }

  // flatMap函数：扁平化
  def demo2(): Unit = {
    flatMap1()
  }

  def flatMap1(): Unit = {
    val list1 = List("World", "Hi")

    val res = list1.flatMap(toUpper)
    println("res = " + res)

    val list2 = List(List("AA", "BB"), List("CC", "EE"))
    val res2 = list2.flatten
    println("res2 = " + res2)

    // 先对集合中的每个集合排序，再做扁平化
    val list3 = List(List(1,2), List(4,3))
    val res3: List[Int] = list3.flatMap(_.sorted)
    println("res3 = " + res3)
  }


  // map 函数：散列
  def demo1(): Unit = {
    map1()
    println("=================")
    map2()
    println("=================")
    map3()

  }

  // 将list中的元素转换为大写返回
  def map3(): Unit = {
    val list = List("Hello", "World", "Hi")
    val res = list.map(_.toUpperCase)
    println("res = " + res)
    println("------------------")
    val res2 = list.map(toUpper)
    println("res2 = " + res2)

  }

  // 自己实现一个map函数
  def map2(): Unit = {
    val myList = MyList()
    val res = myList.map(map1_1)
    println("res = " + res)

  }

  def map1(): Unit = {
    val list = List(2, 3, 4)
    // 1、将list的元素依次遍历传入map1_1函数执行
    // 2、map1_1函数处理每个元素并返回,返回的元素被封装到一个新集合中
    val res = list.map(map1_1)
    println("res = " + res)

  }

  // 返回入参的2倍
  def map1_1(n: Int): Int = {
    println("map1_1 被调用")
    2 * n
  }

  // 将字符串转换为大写返回
  def toUpper(str: String): String = {
    str.toUpperCase
  }

  // 判断字符串是否以大写字母开头
  def isStartWithU(str: String): Boolean = {
    str.charAt(0).isUpper
  }

  // 两个数求和
  def sum(n1: Int, n2: Int): Int = {
    println(s"n1=$n1, n2=$n2")
    n1 + n2
  }

  // 两个数求差值
  def dif(n1: Int, n2: Int): Int = {
    println(s"n1=$n1, n2=$n2")
    n1 - n2
  }

  // 求两个数的最小数
  def min(n1: Int, n2: Int): Int = {
    if (n1 >= n2) {
      n2
    } else {
      n1
    }
  }

}

// 自己实现List的map函数
class MyList {
  val list = List(2, 3, 4)
  var list2 = List[Int]()

  def map(f: Int => Int): List[Int] = {
    for (item <- list) {
      list2 = list2 :+ f(item)
    }
    list2
  }
}

object MyList {
  def apply(): MyList = new MyList()

}
