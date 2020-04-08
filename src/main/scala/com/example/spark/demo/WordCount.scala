package com.example.spark.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2017/5/10.
  *
  */
object WordCount {
  def main(args: Array[String]): Unit = {
    //1. 创建SparkConf对象
    val conf = new SparkConf()
    conf.setAppName("WordCount")    //设置应用程序名称
    conf.setMaster("local")         //设置本地模式

    //2. 创建SparkContent对象，初始化spark程序运行所需的核心组件
    val sc = new SparkContext(conf)

    //3. 根据具体的数据来源来创建RDD
    //参数1:文件路径   参数2:并行度（分片）
    val lines = sc.textFile("E:\\file\\spark\\WordCount.txt")

    //4.1 .对初始RDD进行转换处理
    val words = lines.flatMap(line => line.split(" "))  //将每一行切分成单词，并把切分结果合并为Map集合

    //4.2 .对单词进行计数
    val pairs = words.map(word => (word, 1))
    val wordCount = pairs.reduceByKey(_+_).map(pair => (pair._2,pair._1)).sortByKey(false).map(pair => (pair._2,pair._1))

    //打印内容
    wordCount.collect.foreach(wordPairs => println(wordPairs._1+" : "+ wordPairs._2))

    //关闭资源
    sc.stop()


  }

}
