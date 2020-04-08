package com.example.scala.demo3

/**
  * 将JavaMap中的数据复制到ScalaMap中
  */
object JavaMap2ScalaMap extends App {
  // 同名类重命名
  import java.util.{HashMap => JavaHashMap}
  import collection.mutable.{HashMap => ScalaHashMap}

  val javaMap = new JavaHashMap[Int, String]()
  javaMap.put(1, "111")
  javaMap.put(2, "222")
  javaMap.put(3, "333")
  javaMap.put(4, "444")

  val scalaMap = new ScalaHashMap[Int, String]()

  for (key <- javaMap.keySet().toArray) {
    //    scalaMap.put(key.asInstanceOf[Int], javaMap.get(key))
    scalaMap += (key.asInstanceOf[Int] -> javaMap.get(key))
  }

  println(scalaMap)

}
