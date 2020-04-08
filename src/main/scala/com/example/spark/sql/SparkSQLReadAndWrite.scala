package com.example.spark.sql

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * SparkSQL读取及保存数据
  *
  */
object SparkSQLReadAndWrite {

  /**
    * jdbc操作: 读取
    *
    */
  def my_jdbc_read(spark: SparkSession): Unit = {
    val options = Map[String, String](
      ("url", "jdbc:mysql://localhost:3306/spark"),
      ("driver", "com.mysql.jdbc.Driver"),
      ("dbtable", "user"),
      ("user", "root"),
      ("password", "password")
    )

    //读取
    val df: DataFrame = spark.read.format("jdbc").options(options).load()
    df.show()

  }

  /**
    * jdbc操作: 写出
    *
    */
  def my_jdbc_write(spark: SparkSession): Unit = {
    val properties: Properties = new Properties()
    properties.put("user", "root")
    properties.put("password", "root")

    val url: String = "jdbc:mysql://localhost:3306/spark"
    val table: String = "user"

    // 导入转换包
    import spark.implicits._
    //写入
    val rdd: RDD[(String, Int)] = spark.sparkContext.makeRDD(List(("aa", 30), ("bb", 40)))
    val df: DataFrame = rdd.toDF("name", "age")
    df.write.mode("append").jdbc(url, table, properties)

  }

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL-R-W")

    // 上下文对象
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    my_jdbc_write(spark)
    //    my_jdbc_read(spark)

    // 关闭资源
    spark.stop()
  }


}
