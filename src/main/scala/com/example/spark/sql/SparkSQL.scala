package com.example.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}
import org.apache.spark.sql._


/**
  * SparkSQL基本操作
  * RDD、DataFrame、DateSet之间的转换
  * 自定义UDAF函数：弱类型，强类型
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

  /**
    * 自定义UDAF（弱类型）
    *
    */
  def my_udaf(spark: SparkSession): Unit ={
    // 导入转换包
    import spark.implicits._
    // 创建RDD
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1,"aa",20),(2,"bb",30),(3,"cc",40)))

    // RDD转DF
    val df: DataFrame = rdd.toDF("id","name","age")
    df.createTempView("user")

    // 创建及注册自定义UDAF函数
    val myAvg: MyAgeAvgFunction = new MyAgeAvgFunction
    spark.udf.register("myAvg", myAvg)

    spark.sql("select myAvg(age) from user").show()
  }


  /**
    * 自定义UDAF(强类型)
    *
    */
  def my_udaf_class(spark: SparkSession): Unit ={
    // 导入转换包
    import spark.implicits._
    // 创建RDD
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1,"aa",20),(2,"bb",30),(3,"cc",40)))
    val map: RDD[UserBean] = rdd.map(t => {UserBean(t._1, t._2, t._3)})

    // 创建DataSet
    val ds: Dataset[UserBean] = map.toDS()

    // 创建自定义UDAF函数对象
    val udaf: MyAgeAvgClassFunction = new MyAgeAvgClassFunction
    // 将聚合函数转为列查询
    val avgCol: TypedColumn[UserBean, Double] = udaf.toColumn.name("avgAge")

    // 只能使用DSL风格语法
    ds.select(avgCol).show()

  }

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")

    // 上下文对象

    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    my_udaf_class(spark)
//    my_udaf(spark)
//    transform(spark)
//    readJson(spark)

    // 关闭资源
    spark.stop()
  }

}

// 样例类
case class User(id:Int, name:String, age:Int)

/**
  *自定义UDAF(强类型)-输入类型
  */
case class UserBean(id:Int, name:String, age:Int)

/**
  * 自定义UDAF(强类型)-缓冲区类型
  * 注意：属性为var
  */
case class AvgBuffer(var sum: Int, var count: Int)

/**
  * 自定义UDAF(强类型)
  */
class MyAgeAvgClassFunction extends Aggregator[UserBean, AvgBuffer, Double]{
  // 初始化缓冲区
  override def zero: AvgBuffer = AvgBuffer(0,0)

  // 更新缓冲区数据
  override def reduce(b: AvgBuffer, a: UserBean): AvgBuffer = {
    b.sum += a.age
    b.count += 1
    b
  }

  // 合并多个分区的缓冲区
  override def merge(b1: AvgBuffer, b2: AvgBuffer): AvgBuffer = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }

  // 计算最终结果
  override def finish(reduction: AvgBuffer): Double = {
    reduction.sum.toDouble / reduction.count
  }

  // 转码
  override def bufferEncoder: Encoder[AvgBuffer] = Encoders.product
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}


/**
  * 自定义UDAF(弱类型)
  */
class MyAgeAvgFunction extends UserDefinedAggregateFunction{
  //输入数据结构
  override def inputSchema: StructType = {
    new StructType().add("age", LongType)
  }

  //计算时的数据结构
  override def bufferSchema: StructType = {
    new StructType().add("sum", LongType).add("count", LongType)
  }

  //计算完毕之后的数据类型
  override def dataType: DataType = DoubleType

  //函数是否稳定
  override def deterministic: Boolean = true

  //计算之前缓冲区初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }

  //根据结果更新缓冲区数据
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    buffer(1) = buffer.getLong(1) + 1L
  }

  //将多个节点的缓冲区合并的一起
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  //结算最终结果
  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble / buffer.getLong(1)
  }
}