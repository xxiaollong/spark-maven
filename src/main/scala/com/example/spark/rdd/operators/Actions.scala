package com.example.spark.rdd.operators

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.Map

/**
  * 行动算子
  *
  * 本质上在 Action 算子中通过 SparkContext 进行了提交作业的 runJob 操作，触发了RDD DAG 的执行
  *
  */
object Actions {

  /**
    * reduce: 化简操作
    *
    */
  def my_reduce(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val reduce: Int = rdd.reduce(_ + _)
    println("reduce: " + reduce)

  }

  /**
    * collect: 收集，返回一个数组
    *
    */
  def my_collect(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val collect: Array[Int] = rdd.collect()
    println(collect.mkString(","))
  }

  /**
    * count: 求一个数据集中元素个数，返回值默认Long类型
    *
    */
  def my_count(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val count: Long = rdd.count()
    println("count: " + count)

  }


  /**
    * 统计一个RDD中各个key的出现次数
    *
    */
  def my_countByKey(sc: SparkContext): Unit = {
    val rdd: RDD[(Int, String)] = sc.makeRDD(List((1, "aa"), (3, "cc"), (2, "bb"), (3, "cc"), (1, "aa")), 2)
    val keyCount: Map[Int, Long] = rdd.countByKey()
    keyCount.foreach(println)
  }

  /**
    * 统计一个RDD中各个元素的出现次数
    *
    */
  def my_countByValue(sc: SparkContext): Unit = {
    val rdd: RDD[(Int, String)] = sc.makeRDD(List((1, "aa"), (3, "cc"), (2, "bb"), (3, "cc"), (1, "aa")), 2)
    val count: Map[(Int, String), Long] = rdd.countByValue()
    count.foreach(println)

  }


  /**
    * first: 返回数据集的第一个元素（类似于take(1)）
    *
    */
  def my_first(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val first: Int = rdd.first()
    println("first: " + first)

  }

  /**
    * take(n): 返回一个包含数据集前n个元素的数组（从0下标到n-1下标的元素），不排序
    *
    */
  def my_take(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val take: Array[Int] = rdd.take(3)
    println(take.mkString(","))

  }

  /**
    * takeSample（withReplacement, num, [seed]）: 对于一个数据集进行随机抽样，返回一个包含num个随机抽样元素的数组，
    * withReplacement: 表示是否有放回抽样，
    * seed: 指定生成随机数的种子
    *
    */
  def my_takeSample(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val sample: Array[Int] = rdd.takeSample(true, 3, 1)
    println(sample.mkString(","))
  }

  /**
    * takeOrdered: 返回RDD中前n个元素，并按默认顺序排序（升序）或者按自定义比较器顺序排序
    *
    */
  def my_takeOrdered(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(Array(5, 2, 66, 3, 44, 2, 6), 1)
    val ordered1: Array[Int] = rdd.takeOrdered(3)
    println(ordered1.mkString(","))

  }

  /**
    * top: 返回最大的k个元素,底层调用了takeOrdered(num)(ord.reverse)
    *
    */
  def my_top(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(Array(5, 2, 66, 3, 44, 2, 6), 1)
    val ordered1: Array[Int] = rdd.top(3)
    println(ordered1.mkString(","))
  }


  /**
    * aggregate(zeroValue)(seqOp, combOp): 聚合， aggregate先对每个分区的所有元素进行aggregate操作，再对分区的结果进行fold操作。
    * zeroValue: 初始值
    * seqOp: 分区内操作
    * combOp: 分区间操作
    *
    * 注意：aggregate中分区内及分区间zeroValue都参与运算，而aggregateByKey中zeroValue只在分区内参与运算
    *
    * 注意：aggreagate与fold和reduce的不同之处在于，aggregate相当于采用归并的方式进行数据聚集，这种聚集是并行化的。
    * 而在fold和reduce函数的运算过程中，每个分区中需要进行串行处理，每个分区串行计算完结果，
    * 结果再按之前的方式进行聚集，并返回最终聚集结果
    *
    * 案例：求每个分区最大值的和
    */
  def my_aggregate(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(Array(5, 2, 66, 3, 44, 2, 6), 2)
    rdd.glom().collect().foreach(item => println(item.toList))

    val aggregate: Int = rdd.aggregate(0)(math.max, _ + _)
    println("aggregate :" + aggregate)

  }

  /**
    * fold(zeroValue)(op): 聚合，与aggregate的区别是分区内和分区间的操作相同
    * zeroValue: 初始值
    * op: 分区内及分区间操作
    *
    * 注意: fold中分区内及分区间zeroValue都参与运算，而foldByKey中zeroValue只在分区内参与运算
    *
    */
  def my_fold(sc: SparkContext): Unit = {
    val rdd: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val fold: Int = rdd.fold(0)(_ + _)
    println("fold: " + fold)
  }

  /**
    * saveAsTextFile: 将dataSet中元素以文本文件的形式写入本地文件系统或者HDFS等
    *
    */
  def my_saveAsTextFile(sc: SparkContext): Unit = {
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((11, "aa"), (22, "bb"), (33, "cc")), 2)
    rdd.saveAsTextFile("saveAsTextFile")
  }

  /**
    * saveAsSequenceFile: 将元素以Hadoop SequenceFile的形式写入本地文件系统或者HDFS等
    *
    */
  def my_saveAsSequenceFile(sc: SparkContext): Unit = {
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((11, "aa"), (22, "bb"), (33, "cc")), 2)
    rdd.saveAsSequenceFile("saveAsSequenceFile")
  }

  /**
    * saveAsObjectFile: 将数据集中元素以ObjectFile形式写入本地文件系统或者HDFS等
    *
    */
  def my_saveAsObjectFile(sc: SparkContext): Unit = {
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((11, "aa"), (22, "bb"), (33, "cc")), 2)
    rdd.saveAsObjectFile("saveAsObjectFile")
  }

  /**
    * foreach(function): 对数据集中每一个元素运行函数function
    *
    * 注意：理解foreach中function的执行位置
    *
    */
  def my_foreach(sc: SparkContext): Unit = {

    val rdd: RDD[Int] = sc.makeRDD(1 to 5, 2)
    rdd.foreach(i => {
      // 此处的foreach是spark中的算子
      println(i * 2) // 在executor中执行
    })

    rdd.collect().foreach(i => {
      // 此处的foreach是scala中的
      println(i * 2) // 在driver中执行
    })

  }


  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Actions")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

    //    my_saveAsTextFile(sc)
    //    my_saveAsSequenceFile(sc)
    //    my_saveAsObjectFile(sc)
    //    my_fold(sc)
    //    my_aggregate(sc)
    //    my_top(sc)
    //    my_takeSample(sc)
    //    my_take(sc)
    //    my_first(sc)
    //    my_count(sc)
    my_countByKey(sc)
    my_countByValue(sc)
    //    my_collect(sc)
    //    my_reduce(sc)

    sc.stop()

  }


}
