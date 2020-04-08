package com.example.scala.demo7

import scala.collection.mutable.ArrayBuffer

/**
  * 数组
  *
  *   定长数组：Array
  *   变长数组：ArrayBuffer
  *
  */
object ArrayDemo01 {

  def main(args: Array[String]): Unit = {
    //    demo1()
    //    demo2()
    //    demo3()
    //    demo4()
    //    demo5()
    demo6()

  }

  // scala数组转换为java的List
  def demo6(): Unit = {
//    val arr = ArrayBuffer(20, 30, 40)
//    import scala.collection.JavaConversions.bufferAsJavaList
//    val javaArr = new ProcessBuilder(arr)
//    val list = javaArr.command()
//    println(list)
  }

  // 多维数组
  def demo5(): Unit = {
    // 创建多维数组
    val arr1 = Array.ofDim[Any](2, 3)

    // 添加元素
    arr1(0)(0) = 10
    arr1(0)(1) = "hello"
    arr1(0)(2) = true
    // arr1(0)(3) = true  // 3下标越界

    arr1(1)(0) = 0
    arr1(1)(1) = "world"
    arr1(1)(2) = 10.3
    // arr1(2)(0) = true  // 2下标越界

    // 多维数组遍历 -- for遍历
    for (i0 <- arr1) {
      for (i1 <- i0) {
        print(i1 + "\t")
      }
      println()
    }
    println("-----------------")

    // 多维数组遍历 -- 下边遍历
    for (i <- arr1.indices;
         j <- arr1(0).indices) {
      println(s"arr1($i)($j) = ${arr1(i)(j)}")
    }
  }

  // 定长数组和变长数组的转换
  // 注意：数组本身不会改变，返回的结果为新数组
  def demo4(): Unit = {
    // 声明一个定长数组
    val arr1 = Array[Int](10, 20, 30)

    // 声明一个变长数组
    val arr2 = ArrayBuffer[Int](50, 60, 70, 80)

    // 将一个定长数组转换为变长数组,
    val arr1_1 = arr1.toBuffer

    // 将一个变长数组转换为定长数组
    val arr2_1 = arr2.toArray

    val arr3 = Array(21, true, "hello")
    val arr3_1 = arr3.toBuffer
    val arr3_2 = arr3.toBuffer[Any]

    val arr4 = ArrayBuffer(49, "world", 23.8, false)
    val arr4_1 = arr4.toArray
    val arr4_2 = arr4.toArray[Any]

  }

  // 变长数组 - AarrayBuffer
  def demo3(): Unit = {
    val arr1 = ArrayBuffer[Int](5)
    println("arr1.length = " + arr1.length)
    val arr2 = ArrayBuffer[Int]()
    println("arr2.length = " + arr2.length)
    val arr3 = ArrayBuffer(10, 20, true, "hello")
    println("arr3.length = " + arr3.length)

    // 添加元素
    arr2.append(10)
    arr2.append(20)
    arr2.append(30, 40, 50)
    println(arr2.toString())

    // 获取元素
    println("arr2(1) = " + arr2(1))

    // 修改元素
    arr2(0) = 100
    println(arr2.toString())

    // 删除元素,根据下标删除
    arr2.remove(0)
    println(arr2.toString())

    // 遍历元素
    for (i <- arr2) {
      println(i)
    }

  }

  // 定长数组 - 使用apply()方法创建数组
  def demo2(): Unit = {
    val arr1 = Array(10, 20, 30, true, "hello")
    for (i <- arr1) {
      println(i)
    }
  }

  // 定长数组 - 使用new创建数组
  def demo1(): Unit = {
    // 创建一个数组
    // [Int]:标示泛型
    // (10):标示该数组长度为10
    val arr1 = new Array[Int](10)

    // 设置一个数组元素
    arr1(0) = 10
    arr1(2) = 30

    // 获取一个数组元素
    println("arr1(1) = " + arr1(1))
    println("arr1(2) = " + arr1(2))

    // 获取数组长度
    println("arr1.length = " + arr1.length)

    // 数组遍历
    for (i <- arr1) {
      println(i)
    }

    // 数组遍历2, 包含步长
    for (i <- Range(0, arr1.length, 2)) {
      println(s"arr1($i) = ${arr1(i)}")
    }
  }

}
