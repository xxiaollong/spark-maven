package com.example.spark.rdd.operators

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

import scala.collection.Map


/**
  * K-V 类型算子
  *
  */
object KeyValues {

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("KeyValues")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

    //    my_partitionBy(sc)
    //    my_groupByKey(sc)
    //    my_reduceByKey(sc)
    //    my_aggregateByKey(sc)
    //    my_foldByKey(sc)
    //    my_combineByKey(sc)
    //    my_sortByKey(sc)
    //    my_mapValues(sc)
    //    my_join(sc)
    //    my_cogroup(sc)
    my_example(sc)

    sc.stop()

  }

  /**
    * 案例：分组topN
    *
    */
  def my_example(sc: SparkContext): Unit = {

    val lines: RDD[String] = sc.textFile("E:/hadoopTestFile/groupCount.txt")
    val wordMap: RDD[(String, String)] = lines.map(line => {
      val split: Array[String] = line.split(" ")
      (split(0), split(1))
    })

    val groupByKey: RDD[(String, Iterable[String])] = wordMap.groupByKey()
    val result: RDD[(String, List[(String, Int)])] = groupByKey.map(item => {
      val map: Map[String, Int] = item._2.map(item => (item, 1)).groupBy(_._1).map(item => (item._1, item._2.size))
      val list: List[(String, Int)] = map.toList.sortBy(_._2).reverse.take(2)

      (item._1, list)
    })

    result.collect().foreach(item => {
      println(item._1, item._2)
    })

  }


  /**
    * cogroup: 每个RDD相同Key的元素分别聚合为一个集合，并且返回两个RDD中对应Key的元素集合的迭代器
    *
    */
  def my_cogroup(sc: SparkContext): Unit = {
    val array_1: Array[(String, Int)] = Array(("aa", 20), ("bb", 30), ("cc", 10))
    val mapRdd_1: RDD[(String, Int)] = sc.makeRDD(array_1, 2)

    val array_2: Array[(String, Int)] = Array(("aa", 20), ("bb", 30), ("ee", 10))
    val mapRdd_2: RDD[(String, Int)] = sc.makeRDD(array_2, 2)

    val array_3: Array[(String, Int)] = Array(("aa", 20), ("aa", 20), ("bb", 30), ("ee", 10))
    val mapRdd_3: RDD[(String, Int)] = sc.makeRDD(array_3, 2)

    val rdd_1: RDD[(String, (Iterable[Int], Iterable[Int]))] = mapRdd_1.cogroup(mapRdd_2)
    rdd_1.glom().collect().foreach(item => println(item.toList))

    val rdd_2: RDD[(String, (Iterable[Int], Iterable[Int]))] = mapRdd_1.cogroup(mapRdd_3)
    rdd_2.glom().collect().foreach(item => println(item.toList))
  }

  /**
    * join: 连接
    *
    */
  def my_join(sc: SparkContext): Unit = {
    val array_1: Array[(String, Int)] = Array(("aa", 20), ("bb", 30), ("cc", 10))
    val mapRdd_1: RDD[(String, Int)] = sc.makeRDD(array_1, 2)

    val array_2: Array[(String, Int)] = Array(("aa", 20), ("bb", 30), ("cc", 10))
    val mapRdd_2: RDD[(String, Int)] = sc.makeRDD(array_2, 2)

    val array_3: Array[(String, Int)] = Array(("aa", 20), ("aa", 20), ("bb", 30), ("ee", 10))
    val mapRdd_3: RDD[(String, Int)] = sc.makeRDD(array_3, 2)


    val rdd_1: RDD[(String, (Int, Int))] = mapRdd_1.join(mapRdd_2)
    rdd_1.glom().collect().foreach(item => println(item.toList))

    val rdd_2: RDD[(String, (Int, Int))] = mapRdd_1.join(mapRdd_3)
    rdd_2.glom().collect().foreach(item => println(item.toList))

    val rdd_3: RDD[(String, (Int, Option[Int]))] = mapRdd_1.leftOuterJoin(mapRdd_3)
    rdd_3.glom().collect().foreach(item => println(item.toList))

    rdd_3.map(_._2._2.getOrElse(0)).collect().foreach(println)

  }


  /**
    * mapValues:针对(Key,Value)型数据中的Value进行Map操作，而不对Key进行处理
    *
    */
  def my_mapValues(sc: SparkContext): Unit = {
    val array: Array[(String, Int)] = Array(("aa", 20), ("bb", 30), ("cc", 10), ("aa", 10), ("bb", 20), ("bb", 10))
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(array, 2)
    mapRdd.glom().collect().foreach(item => println(item.toList))

    val rdd_1: RDD[(String, Int)] = mapRdd.mapValues(_ + 7)
    rdd_1.glom().collect().foreach(item => println(item.toList))

    // 实现WordCount
    val rdd_2: RDD[(String, Int)] = mapRdd.groupByKey().mapValues(_.sum).sortBy(_._2)
    rdd_2.glom().collect().foreach(item => println(item.toList))


  }

  /**
    * sortByKey: 按照key进行排序
    *
    * 案例：按照key进行正序和反序排列
    *
    */
  def my_sortByKey(sc: SparkContext): Unit = {
    val array: Array[(String, Int)] = Array(("aa", 20), ("bb", 30), ("cc", 10), ("aa", 10), ("bb", 20), ("bb", 10))
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(array, 2)
    mapRdd.glom().collect().foreach(item => println(item.toList))

    // 正序
    mapRdd.sortByKey(ascending = true, 1).glom().collect().foreach(item => println(item.toList))
    // 反序
    mapRdd.sortByKey(ascending = false, 1).glom().collect().foreach(item => println(item.toList))

  }

  /**
    * combineByKey: 与foldByKey、aggregateByKey底层原理相同，初始值的规则不同
    *
    * 案例：计算每个key的平均值
    */
  def my_combineByKey(sc: SparkContext): Unit = {
    val array: Array[(String, Int)] = Array(("aa", 20), ("bb", 30), ("cc", 10), ("aa", 10), ("bb", 20), ("bb", 10))
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(array, 2)
    mapRdd.glom().collect().foreach(item => println(item.toList))

    val rdd_1: RDD[(String, (Int, Int))] = mapRdd.combineByKey(
      item => (item, 1),
      (mv: (Int, Int), value) => {
        (mv._1 + value, mv._2 + 1)
      },
      (mc1: (Int, Int), mc2: (Int, Int)) => {
        (mc1._1 + mc2._1, mc1._2 + mc2._2)
      }
    )

    val rdd_2: RDD[(String, Int)] = rdd_1.map(item => (item._1, item._2._1 / item._2._2))
    rdd_2.glom().collect().foreach(item => println(item.toList))

    // 实现求和功能
    val rdd_3: RDD[(String, Int)] = mapRdd.combineByKey(
      item => item,
      (mv: Int, value: Int) => mv + value,
      (mc1: Int, mc2: Int) => mc1 + mc2
    )
    rdd_3.collect().foreach(println)


  }

  /**
    * foldByKey: 与aggregateByKey底层原理相同，调用的都是：combineByKeyWithClassTag，不同的是分区内和分区外使用的规则相同
    * 案例：计算相同key的value相加的结果
    */
  def my_foldByKey(sc: SparkContext): Unit = {
    val array: Array[(String, Int)] = Array(("aa", 2), ("bb", 3), ("cc", 1), ("aa", 1), ("bb", 2), ("bb", 1))
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(array, 2)
    mapRdd.glom().collect().foreach(item => println(item.toList))

    // 实现WordCount案例
    val rdd_2: RDD[(String, Int)] = mapRdd.foldByKey(0)(_ + _)
    rdd_2.glom().collect().foreach(item => println(item.toList))
  }

  /** aggregateByKey(zeroValue)(seqOp, combOp)
    * zeroValue: 初始值
    * seqOp: 分区内
    * combOp: 分区间
    *
    * 案例：取每一个分区内相同key的最大值相加
    *
    */
  def my_aggregateByKey(sc: SparkContext): Unit = {
    val array: Array[(String, Int)] = Array(("aa", 2), ("bb", 3), ("cc", 1), ("aa", 1), ("bb", 2), ("bb", 1))
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(array, 3)
    mapRdd.glom().collect().foreach(item => println(item.toList))

    val rdd_2: RDD[(String, Int)] = mapRdd.aggregateByKey(0)(math.max, _ + _)
    rdd_2.glom().collect().foreach(item => println(item.toList))

    // 实现WordCount案例
    val rdd_3: RDD[(String, Int)] = mapRdd.aggregateByKey(0)(_ + _, _ + _)
    rdd_3.glom().collect().foreach(item => println(item.toList))

  }

  /**
    * reduceByKey: 主要用于按照key值对value进行聚合
    * 区别: reduceByKey主要作用是聚合，groupByKey主要作用是分组
    *
    */
  def my_reduceByKey(sc: SparkContext): Unit = {
    val array: Array[String] = Array("aa", "bb", "cc", "aa", "bb", "bb")
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(array, 2).map(word => (word, 1))
    val rdd_1: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)
    rdd_1.collect().foreach(item => println(item._1 + " : " + item._2))

  }


  /**
    * groupByKey: 将相同的所有的键值对分组到一个集合序列当中，其顺序是不确定的。
    * 注意: 若一个键对应值太多，则易导致内存溢出。
    */
  def my_groupByKey(sc: SparkContext): Unit = {

    val array: Array[String] = Array("aa", "bb", "cc", "aa", "bb", "bb")
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(array, 2).map(word => (word, 1))
    val rdd_1: RDD[(String, Iterable[Int])] = mapRdd.groupByKey()
    rdd_1.collect().foreach(item => println(item._1 + " : " + item._2.toList + " : " + item._2.size))

  }

  /**
    * partitionBy: partitionBy函数对RDD进行分区操作，操作的数据必须是k-v格式
    * 注意：如果原有RDD的分区器和现有分区器（partitioner）不一致，则根据分区器生成一个新的ShuffledRDD，产生shuffle操作。
    *
    */
  def my_partitionBy(sc: SparkContext): Unit = {

    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "aa"), (2, "bb"), (3, "cc"), (4, "dd")), 3)
    rdd.glom().collect().foreach(item => println(item.toList))

    // 使用HashPartitioner分区器
    val rdd_2: RDD[(Int, String)] = rdd.partitionBy(new HashPartitioner(2))
    rdd_2.glom().collect().foreach(item => println(item.toList))

    // 使用自定义分区器
    val rdd_3: RDD[(Int, String)] = rdd.partitionBy(new MyPartitioner(3))
    rdd_3.glom().collect().foreach(item => println(item.toList))

  }

}


/**
  * 自定义分区器
  */
class MyPartitioner(partitions: Int) extends Partitioner {
  override def numPartitions: Int = {
    partitions
  }

  override def getPartition(key: Any): Int = {
    1 //代表所有数据放一个分区，但是分区个数依然是partitions，只不过其他分区无数据
  }
}