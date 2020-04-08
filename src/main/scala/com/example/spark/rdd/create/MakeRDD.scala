package com.example.spark.rdd.create

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 创建RDD
  */
object MakeRDD {

  // 设置日志级别
//  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRDD")

    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

    // 创建RDD
//    val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,4))

    // 创建RDD并指定分区
//    val rdd: RDD[Int] = sc.parallelize(Array(11,22,33,44), 2)

    // 读取文件时指定的最小分区，实际不一定是指定的，取决于hadoop的分片规则
    val rdd: RDD[String] = sc.textFile("E:/Spark/data.txt", 5)

    // 打印RDD
    rdd.collect().foreach(println)
    // 结果保存到文件
//    rdd.saveAsTextFile("outFile")

    // 关闭资源
    sc.stop()
  }

}
