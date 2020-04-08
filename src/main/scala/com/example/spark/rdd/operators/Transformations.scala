package com.example.spark.rdd.operators

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 转换算子
  *
  *
  */
object Transformations {
  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Transformations")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

    //    my_map(sc)
    //    my_mapPartitions(sc)
    //    my_mapPartitionsWithIndex(sc)
    //    my_flatMap(sc)
    //    my_glom(sc)
    //    my_groupBy(sc)
    //    my_filter(sc)
    //    my_sample(sc)
    //    my_distinct(sc)
    //    my_coalesce(sc)
    //    my_repartiton(sc)
    //    my_sortBy(sc)
    //    my_intersection_union_subtract_cartesian(sc)
    //    my_zip(sc)
    //    my_cache_persist(sc)
    my_checkpoint(sc)

    // 关闭资源
    sc.stop()
  }


  /**
    * checkpoint: 设置检查点，需要制定检查点保存的磁盘目录
    *
    * 注意：设置检查点和对应行动算的前后顺序
    */
  def my_checkpoint(sc: SparkContext): Unit = {
    val listRdd: RDD[Int] = sc.makeRDD(1 to 10, 3)
    val mapRdd = listRdd.map((_, 1))

    val byKey = mapRdd.reduceByKey(_ + _)

    // 设置检查点
    sc.setCheckpointDir("checkPoint")
    byKey.checkpoint()

    byKey.collect()
    println(byKey.toDebugString)

  }

  /**
    * cache,persist: 缓存RDD数据，cache()的底层调用persist()
    * persist默认使用: StorageLevel.MEMORY_ONLY方式缓存
    */
  def my_cache_persist(sc: SparkContext): Unit = {

    val listRdd: RDD[Int] = sc.makeRDD(1 to 10, 3)
    val mapRdd = listRdd.map((_, 1))

    // 设置缓存
    mapRdd.cache()
    mapRdd.persist()

    val byKey = mapRdd.reduceByKey(_ + _)
    byKey.collect()
    println(byKey.toDebugString)

  }


  /**
    * zip: 拉链
    * 注意: 1、分区数量相等  2、每个分区中的元素相等
    *
    */
  def my_zip(sc: SparkContext) {
    val RDD1: RDD[Int] = sc.makeRDD(1 to 5, 1)
    val RDD2: RDD[Int] = sc.makeRDD(1 to 10, 2)

    val zip: RDD[(Int, Int)] = RDD1.zip(RDD2)
    zip.collect().foreach(println)

  }


  /**
    * intersection: 求交集
    * union: 求并集
    * subtract: 求合集
    * cartesian: 求笛卡尔积
    *
    */
  def my_intersection_union_subtract_cartesian(sc: SparkContext): Unit = {
    val RDD1: RDD[Int] = sc.makeRDD(1 to 5, 1)
    val RDD2: RDD[Int] = sc.makeRDD(4 to 7, 1)

    val intersection: RDD[Int] = RDD1.intersection(RDD2)
    intersection.collect().foreach(println)

    val union: RDD[Int] = RDD1.union(RDD2)
    union.collect().foreach(println)

    val subtract: RDD[Int] = RDD1.subtract(RDD2)
    subtract.collect().foreach(println)

    val cartesian: RDD[(Int, Int)] = RDD1.cartesian(RDD2)
    cartesian.collect().foreach(println)
  }


  /**
    * sortBy: 排序
    *
    */
  def my_sortBy(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10)
    // 降序
    val newRDD1: RDD[Int] = listRDD.sortBy(item => item, false)
    println(newRDD1.collect().toList)
    val newRDD2: RDD[Int] = listRDD.sortBy(item => item % 3)
    println(newRDD2.collect().toList)

  }


  /**
    * repartiton: 重组分区，减少RDD中分区的数量到numPartitions，用于处理数据倾斜问题
    * 注意：分区都被打乱重组，为shuffle操作
    *
    */
  def my_repartiton(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)
    println("前：" + listRDD.partitions.length + " 值：")
    listRDD.glom().collect().foreach(item => {
      println(item.toList)
    })
    val newRDD: RDD[Int] = listRDD.repartition(2)
    println("后：" + newRDD.partitions.length + " 值：")
    newRDD.glom().collect().foreach(item => {
      println(item.toList)
    })

  }


  /**
    * coalesce: 缩减分区（合并分区），减少RDD中分区的数量到numPartitions
    * 注意：默认是合并分区，1分区内的元素没有被重组，所以该过程不存在shuffle操作
    *
    */
  def my_coalesce(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 9, 8, 2, 3, 4, 8, 9, 3, 5, 2, 1, 4))
    println("前: " + listRDD.partitions.length)
    val newRDD: RDD[Int] = listRDD.coalesce(2)
    println("后: " + newRDD.partitions.length)
    newRDD.collect().foreach(println)

  }


  /**
    * distinct: 返回一个在源数据集去重之后的新数据集，即去重，并局部无序而整体有序返回
    * 注意：新数据的顺序被打乱重组（shuffle操作）
    *
    */
  def my_distinct(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 9, 8, 2, 3, 4, 8, 9, 3, 5, 2, 1, 4))
    val newRDD: RDD[Int] = listRDD.distinct()
    newRDD.collect().foreach(println)

    // 将结果放在两个分区
    val newRDD2: RDD[Int] = listRDD.distinct(2)
    newRDD2.collect().foreach(println)

  }


  /**
    * sample: 采样操作，用于从样本中取出部分数据
    * sample（withReplacement， fraction， seed）
    * ----withReplacement: 是否放回抽样分true和false
    * ----fraction: fraction取样比例为(0, 1]
    * ----seed: 种子(整型实数)
    */
  def my_sample(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10)
    val newRDD: RDD[Int] = listRDD.sample(false, 0.5, 1)

    newRDD.collect().foreach(println)
  }


  /**
    * filter: 对元素进行过滤，对每个元素应用f函数，返回值为true的元素组成一个新的RDD
    *
    */
  def my_filter(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10)
    // 获取偶数集合
    val newRDD: RDD[Int] = listRDD.filter(_ % 2 == 0)
    newRDD.collect().foreach(println)

  }

  /**
    * groupBy: 按照指定的规则进行分组,返回Tuple2类型数据
    *
    */
  def my_groupBy(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10)

    val newRDD: RDD[(Int, Iterable[Int])] = listRDD.groupBy(_ % 3)

    newRDD.collect().foreach(println)
    newRDD.collect().foreach(item => {
      println(item._1 + " : " + item._2.mkString(","))
    })
  }


  /**
    * glom: glom函数将每个分区形成一个数组
    *
    */
  def my_glom(sc: SparkContext): Unit = {
    // 创建一个3个分区的RDD
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10, 3)
    val arrRDD: RDD[Array[Int]] = listRDD.glom()
    arrRDD.collect().foreach(item => {
      println(item.mkString(","))
    })

    // 求每个分区的最大值
    val maxs: RDD[Int] = listRDD.glom().map(_.max)
    maxs.collect().foreach(println)

  }


  /**
    * flatMap: 将原来 RDD 中的每个元素通过函数 f 转换为新的元素，并将生成的 RDD 的每个集合中的元素合并为一个集合
    * flatMap: 先映射，后扁平化
    *
    */
  def my_flatMap(sc: SparkContext): Unit = {
    val rdd1: RDD[List[Int]] = sc.makeRDD(Array(List(1, 2), List(4, 3, 2)))
    val rdd2: RDD[Int] = rdd1.flatMap(item => item)
    rdd2.collect().foreach(println)

    // 体现先映射，后扁平化
    val rdd3: RDD[Int] = rdd1.flatMap(_.sorted)
    rdd3.collect().foreach(println)
  }

  /**
    * mapPartitionsWithIndex: 与mapPartitions类似，但需要提供一个表示分区索引值的整型值作为参数
    *
    * mapPartitionsWithIndex关注数据本身，也兼顾分区
    *
    */
  def my_mapPartitionsWithIndex(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val newRDD: RDD[(Int, Int)] = listRDD.mapPartitionsWithIndex((parNum, item) => {
      println("parNum : " + parNum)
      item.map((parNum, _))
    })

    newRDD.collect().foreach(println)
  }

  /**
    * mapPartitions: 获取到每个分区的迭代器，在函数中通过这个分区整体的迭代器对整个分区的元素进行操作
    *
    * mapPartitions相比map的优点：减少了与执行器（Executor）的交互次数，详见index被调用次数
    *
    * mapPartitions相比map的缺点：若单个分区数据量过大，Executor可能出现内存溢出（整个分区数据处理完后，对应的数据才能释放）
    *
    */
  def my_mapPartitions(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10, 2)
    var index: Int = 1
    val newRDD: RDD[Int] = listRDD.mapPartitions(item => {
      println("index : " + index)
      index += 1
      // 这个map是scala的
      item.map(_ + 10)
    })
    newRDD.collect().foreach(println)

    // 求分区最大值
    val maxs: RDD[Int] = listRDD.mapPartitions(item => {
      List(item.max).toIterator
    })
    maxs.collect().foreach(println)


  }


  /**
    * map: 将原来 RDD 的每个数据项通过 map 中的用户自定义函数 f 映射转变为一个新的元素
    *
    */
  def my_map(sc: SparkContext): Unit = {
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10, 2)
    // 这里index会单独与每个分区绑定，实际每个Executor中都有一个独立的index
    var index: Int = 1
    // 这个map是spark的
    val newRDD: RDD[Int] = listRDD.map(item => {
      println("index : " + index)
      index += 1
      item + 10
    })

    newRDD.collect().foreach(println)
  }

}
