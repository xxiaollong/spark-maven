package com.example.spark.demo

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2017/6/9.
  */
object Transformations {

  def main(args: Array[String]): Unit = {
    val sc = sparkContext("Transformations")
//    mapTransformation(sc)
//    filterTransformation(sc)
//    flatMapTransformation(sc)
//    groupByKeyTransformation(sc)
//      reduceByKeyTransformation(sc)
//      joinTransformation(sc)
      cogroupTransformations(sc)

    //释放资源
    sc.stop()
  }

  def cogroupTransformations(sc : SparkContext){
    val studentL = Array(
      Tuple2(1,"盖聂"),
      Tuple2(2,"卫庄"),
      Tuple2(3,"章邯"),
      Tuple2(4,"韩信"),
      Tuple2(5,"复苏")
    )
    val scoresL = Array(
      Tuple2(1,100),
      Tuple2(2,80),
      Tuple2(3,60),
      Tuple2(4,80),
      Tuple2(6,90),
      Tuple2(2,80),
      Tuple2(3,60),
      Tuple2(4,80),
      Tuple2(6,90),
      Tuple2(4,80),
      Tuple2(6,90)
    )

    val names = sc.parallelize(studentL)
    val scores = sc.parallelize(scoresL)

    val cogroupResult = names.cogroup(scores)
    cogroupResult.collect.foreach(println)

    cogroupResult.collect.foreach(result => println(result._2._2.toArray.foreach(r1 => print(r1+" "))+""
      +result._2._1.toArray.foreach(r2 => print(r2+" "))+" "
      +result._1))
  }

  def joinTransformation(sc : SparkContext){
    val studentL = Array(
      Tuple2(1,"盖聂"),
      Tuple2(2,"卫庄"),
      Tuple2(3,"章邯"),
      Tuple2(4,"韩信"),
      Tuple2(5,"复苏")
    )
    val scoresL = Array(
      Tuple2(1,100),
      Tuple2(2,80),
      Tuple2(3,60),
      Tuple2(4,80),
      Tuple2(4,80),
      Tuple2(6,90)
    )

    val names = sc.parallelize(studentL)
    val scores = sc.parallelize(scoresL)
    //根据Key关联value
    val joinResult = names.join(scores)
//    val joinResult = names.leftOuterJoin(scores)
//    val joinResult = names.rightOuterJoin(scores)
    joinResult.collect.foreach(println)
  }

  def reduceByKeyTransformation(sc : SparkContext){
    val lines = sc.textFile("E:/file/spark/WordCount.txt")
    val lineMap = lines.map(line => (line,1))
    //按照相同的key对value进行统计
    val lineCount = lineMap.reduceByKey(_+_)
    lineCount.collect.foreach(pair => println(pair._1+" : "+pair._2))
  }

  def groupByKeyTransformation(sc : SparkContext){
    val data = Array(Tuple2(100,"Spark"),Tuple2(10,"hadoop"),Tuple2(80,"Hive"),Tuple2(80,"Steam"),Tuple2(100,"Java"))
    val dataRDD = sc.parallelize(data);
    //按照相同的key对value进行分组，分组后的value是一个集合
    val group = dataRDD.groupByKey()
    group.collect.foreach(println)
  }

  def sparkContext(name : String )={
    //初始化程序的配置
    val conf = new SparkConf().setAppName(name).setMaster("local")
    //第一个RDD创建的唯一入口，是通往集群的唯一通道
    val sc = new SparkContext(conf)
    sc
  }

  def mapTransformation(sc : SparkContext){
    val nums = sc.parallelize(1 to 10)
    val map = nums.map(item => 2 * item)
    //map适用于任何类型的元素，且对其作用的集合中的每个元素遍历，将遍历的每个元素作为参数调用相应的函数进行对应的操作
    val mapped = map
    mapped.collect.foreach(println)
  }

  def filterTransformation(sc : SparkContext){
    val nums = sc.parallelize(11 to 20)
    //filter函数参数是一个Boolean类型的值,根据该参数过滤出符合条件的元素过程新集合
    val filtered = nums.filter(item => item%2 == 0)
    filtered.collect.foreach(println)
  }

  def flatMapTransformation(sc : SparkContext){
    val bigData = Array("Scala Spark","Java Hadoop","Python Diango")
    val liens = sc.parallelize(bigData)
    //使用分隔符将数据分割成K-V形式的数据
    val words = liens.flatMap(line => line.split(" "))
    words.collect.foreach(println)
  }



}
