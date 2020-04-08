package com.example.scala.demo7

import scala.collection.mutable

/**
  * 映射
  * 不可变Map: 默认Map为不可变Map，不需要导包，有序，底层为Tuple2
  * 可变Map: 需要导包，无序
  *   注意：
  *     1、Map获取元素时：如果确定key存在，则使用map(key),如果不确定，则使用map4.getOrElse()
  */
object MapDemo01 {
  def main(args: Array[String]): Unit = {
    //        demo1()
    demo2()

  }

  // 可变Map
  def demo2(): Unit = {

    // 创建
    val map1 = mutable.Map[Any, Any]() // 声明一个空Map，注意()
    val map2 = mutable.Map[String, Int]("aa" -> 11, "bb" -> 11, "cc" -> 33)
    val map3 = mutable.Map("aa" -> 11, true -> 11, 24 -> "hello")
    val map4 = mutable.Map(("aa", 11), (true, 11), (24, "hello")) // 使用元组的方式创建

    println("map1 : " + map1)
    println("map4 : " + map4)



    // 获取元素
    println("map4(\"aa\") = " + map4("aa"))
    // 当获取的key不存在时抛出异常：key not found
    //    println("map4(\"bb\") = " + map4("bb"))
    // 判断key是否存在
    println("map4.contains(\"bb\") = " + map4.contains("bb"))
    println("map4.get(\"aa\") = " + map4.get("aa")) //此处返回Some类型数据，或者None
    println("map4.get(\"aa\").get = " + map4.get("aa").get) //若为None类型则抛出异常None.get
    println("map4.getOrElse(\"aa\", \"默认值\") = " + map4.getOrElse("aa", "默认值"))
    println("map4.getOrElse(\"bb\", \"默认值\") = " + map4.getOrElse("bb", "默认值"))




    // 添加元素-修改元素（存在则修改，不存在则添加）
    map4(false) = "false"
    map4("aa") = "aa"
    map4 += ("100" -> 100)
    println("map4 : " + map4)

    // 添加多组元素
    map4 += ("100" -> 100, "CC" -> "CC")
    println("map4 : " + map4)

    // 添加一个map
    map4 ++= map2
    println("map4 : " + map4)




    // 删除元素
    map4.remove("aa")
    map4 -= "bb"
    // 删除多个，key可以写多个
    map4 -= ("cc", "hello")
    println("map4 : " + map4)

    // 这种方式map本身不会改变，产生一个新map
    val map5 = map4 - "CC"
    println("map4 : " + map4)
    println("map5 : " + map5)
    var map6 = map4 - (true, false)
    println("map4 : " + map4)
    println("map6 : " + map6)
    // 若map使用var声明，则可以使用这种删除元素的方式
    map6 = map6 - "CC"
    println("map6 : " + map6)



    // Map遍历
    println("Map遍历方式--1：遍历成元组")
    for((k,v) <- map6){
      println(s"$k -> $v")
    }

    println("Map遍历方式--2：遍历出key")
    for(k <- map6.keys){  // 迭代器
      println(s"$k -> ${map6(k)}")
    }
    for(k <- map6.keySet){  // Set集合
      println(s"$k -> ${map6(k)}")
    }
    println("Map遍历方式--3：遍历出value")
    for(v <- map6.values){
      println(v)
    }
    println("Map遍历方式--4：遍历成元组")
    for(item <- map6){
      println(item)
      println(s"${item._1} -> ${item._2}")
    }

  }

  // 不可变Map
  def demo1(): Unit = {
    // 默认Map是immutable.Map(不可变数组)
    // key-value类型支持Any
    // 每一对k-v是一个Tuple2

    // 创建
    val map1 = Map() // 注意后边的()
    val map2 = Map[String, Int]("aa" -> 11, "bb" -> 11, "cc" -> 33)
    val map3 = Map("aa" -> 11, true -> 11, 24 -> "hello")

    println("map3 : " + map3)

    // 获取元素
    println("map3(\"aa\") = " + map3("aa"))

    // 遍历元素
    for (item <- map3) {
      println(s"${item._1} -> ${item._2}")
    }

    println("-------------------")

    for ((k, v) <- map3) {
      println(s"$k -> $v")
    }


  }

}
