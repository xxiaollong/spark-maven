package com.example.spark.rdd.sharedata

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * 广播变量: 变量一旦被定义为一个广播变量，那么这个变量只能读，不能修改
  *
  */
object Broadcast {


  /**
    * join演示根据key合并两个rdd数据
    *
    * 问题：join首先会有shuffle操作，之后产生大表（笛卡尔积），所以效率很低
    */
  def my_join(sc: SparkContext): Unit = {
    val listRDD1: RDD[(Int, String)] = sc.makeRDD(List((1, "aa"), (2, "bb"), (2, "cc"), (3, "dd")))
    val listRDD2: RDD[(Int, String)] = sc.makeRDD(List((1, "11"), (1, "111"), (2, "22"), (3, "33"), (4, "44")))

    val join: RDD[(Int, (String, String))] = listRDD1.join(listRDD2)
    join.collect().foreach(println)

  }


  /**
    * 创建广播变量
    *
    * 案例：使用广播变量+map实现join操作
    */
  def create(sc: SparkContext): Unit = {
    val listRDD1: RDD[(Int, String)] = sc.makeRDD(List((1, "aa"), (2, "bb"), (2, "cc"), (3, "dd")))
    val list: List[(Int, String)] = List((1, "11"), (1, "111"), (2, "22"), (3, "33"), (4, "44"))

    // 这里将一个RDD创建广播变量
    val broadcast: Broadcast[List[(Int, String)]] = sc.broadcast(list)

    val map1: RDD[ListBuffer[(Int, (String, String))]] = listRDD1.map {
      case (key, value) => {
        val buffer: ListBuffer[(Int, (String, String))] = ListBuffer()
        // 使用广播变量
        for (i <- broadcast.value) {
          if (key == i._1) {
            buffer += ((key, (value, i._2)))
          }
        }

        buffer
      }
    }

    val map2: RDD[(Int, (String, String))] = map1.flatMap(item => item)

    map2.collect().foreach(println)

  }


  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Broadcast")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

//    create(sc)
        my_join(sc)

    sc.stop()

  }
}
