package com.example.spark.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 求TopN
  */
object TopNBasic {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    conf.setAppName("TopNBasic")
    conf.setMaster("local")

    val sc = new SparkContext(conf)

    val lines = sc.textFile("E:/file/spark/TopNData.txt")
    //生成K-V键值对，方便排序
    val pairs = lines.map(line => (line.toInt, line)).distinct()

    val sortPairs = pairs.sortByKey(false)
    //过滤出内容
    val sortData = sortPairs.map(pair => pair._2)
    //获取前5
    val top5 = sortData.take(5)

    //take返回的是元素内容本身构建的Array，不需要Collect()收集元素
    top5.foreach(println)

  }

}

/**
  * 求分组TopN
  */
object groupTopN{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("groupTopN")
    conf.setMaster("local")

    val sc = new SparkContext(conf)

    val lines = sc.textFile("E:/file/spark/groupData.txt")

    val pairs = lines.filter(line => line != null && !"".equals(line))
      .map(line => (line.split(" ")(0), line.split(" ")(1).toInt))

    val groupPairs = pairs.groupByKey()

    val groupValue = groupPairs.map(group => (group._1, group._2.toList.sortWith(_>_).take(5)))

    groupValue.collect.foreach(gv => {
      println("Name: "+gv._1)
      print("TOP5: ")
      gv._2.foreach(va => print(va + " "))
      println()
      })

    sc.stop()
  }
}
