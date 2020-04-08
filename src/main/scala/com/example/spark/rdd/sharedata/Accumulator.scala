package com.example.spark.rdd.sharedata

import java.{lang, util}

import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, CollectionAccumulator, DoubleAccumulator, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * 累加器: 累加器在Driver端定义赋初始值，累加器只能在Driver端读取最后的值，在Excutor端更新
  *
  */
object Accumulator {

  /**
    * 全局变量错误使用演示
    *
    */
  def showErr(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val reduce: Int = listRDD.reduce(_ + _)
    println("reduce : " + reduce)

    // 错误演示
    var sum = 0
    listRDD.foreach(i => {
      sum += i
    })
    println("sum : " + sum)

  }


  /**
    * 创建累加器
    *
    */
  def create(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10, 2)

    // 定义累加器
    val longAccumulator: LongAccumulator = sc.longAccumulator
    val doubleAccumulator: DoubleAccumulator = sc.doubleAccumulator

    listRDD.foreach(i => {
      longAccumulator.add(i.toLong)
      doubleAccumulator.add(i.toDouble)
    })

    println("longAccumulator : " + longAccumulator + "  value: " + longAccumulator.value)
    println("doubleAccumulator : " + doubleAccumulator + " value: " + doubleAccumulator.value)

  }


  /**
    * 自定义累加器
    *
    */
  def my_accumulator(sc: SparkContext): Unit = {
    val listRDD: RDD[String] = sc.makeRDD(List("aaa", "bbb", "abc", "ccc", "bba"), 2)

    // 创建累加器
    val wordAccumulator: WordAccumulator = new WordAccumulator

    // 注册累加器
    sc.register(wordAccumulator)

    listRDD.foreach(i => {
      wordAccumulator.add(i)
    })

    println("wordAccumulator : " + wordAccumulator.value)

  }

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Accumulator")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

    my_accumulator(sc)
    //    create(sc)
    //    showErr(sc)

    sc.stop()
  }
}

/**
  * 自定义累加器
  */
class WordAccumulator extends AccumulatorV2[String, ListBuffer[String]] {
  val list = new ListBuffer[String]

  override def isZero: Boolean = {
    list.isEmpty
  }

  override def copy(): AccumulatorV2[String, ListBuffer[String]] = {
    val tmp: WordAccumulator = new WordAccumulator()
    list.foreach(tmp.list += _)
    tmp
  }

  override def reset(): Unit = {
    list.clear()
  }

  override def add(v: String): Unit = {
    if (v.contains("a")) {
      list += v
    }
  }

  override def merge(other: AccumulatorV2[String, ListBuffer[String]]): Unit = {
    list ++= other.value
  }

  override def value: ListBuffer[String] = list
}
