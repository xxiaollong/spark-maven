package com.example.spark.rdd.jdbc

import org.apache.spark.{SparkConf, SparkContext}

/**
  * SparkRDD操作Redis
  */
object SparkRDDForRedis {



  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkRDDForRedis")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)



    sc.stop()
  }

}
