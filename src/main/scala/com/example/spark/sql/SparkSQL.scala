package com.example.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}


/**
  * SparkSQL基本操作
  *
  */
object SparkSQL {

  /**
    * 读取JSON文件
    *
    */
  def readJson(spark: SparkSession): Unit ={

    val df: DataFrame = spark.read.json("E:/Spark/user_json.txt")
    df.show()

    // 创建表
    df.createOrReplaceTempView("user")

    // 使用SQL的方式访问表
    spark.sql("select * from user").show()

  }

  /**
    * RDD、DataFrame、DateSet之间的装换
    *
    */
  def transform(spark: SparkSession): Unit ={
    // 导入转换包
    import spark.implicits._
    // 创建RDD
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1,"aa",20),(2,"bb",30),(3,"cc",40)))

    // RDD转DF
    val rdd_df: DataFrame = rdd.toDF("id","name","age")

    // RDD转DS
    val rddMap: RDD[User] = rdd.map(t => {User(t._1, t._2, t._3)})
    val rdd_ds: Dataset[User] = rddMap.toDS()

    // DF转RDD
    val df_rdd: RDD[Row] = rdd_df.rdd
    // 可以通过索引获取此处RDD中的数据
    df_rdd.collect().foreach(i => {println(i.getString(1))})

    // DF转DS
    val df_ds: Dataset[User] = rdd_df.as[User]

    // DS转RDD
    val ds_rdd: RDD[User] = rdd_ds.rdd

    // DS转DF
    val ds_df: DataFrame = rdd_ds.toDF()

  }

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")

    // 上下文对象

    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    transform(spark)
//    readJson(spark)

    // 关闭资源
    spark.stop()
  }

}

case class User(id:Int, name:String, age:Int)
