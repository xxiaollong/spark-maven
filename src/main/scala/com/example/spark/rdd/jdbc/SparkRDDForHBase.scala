package com.example.spark.rdd.jdbc

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos.Result
import org.apache.spark.{SparkConf, SparkContext}

/**
  * SparkRDD操作HBase
  */
object SparkRDDForHBase {

  /**
    * HBase: 写操作
    *
    */
  def my_HBase_Save(sc: SparkContext, hbaseConf: Configuration): Unit ={

  }

  /**
    * HBase: 读操作
    *
    */
  def my_HBase_Select(sc: SparkContext, hbaseConf: Configuration): Unit ={
    hbaseConf.set(TableInputFormat.INPUT_TABLE, "movie_wordcount")




  }

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkRDDForHBase")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

    // HBase配置对象
    val hbaseConf: Configuration = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "")

    sc.stop()
  }




}
