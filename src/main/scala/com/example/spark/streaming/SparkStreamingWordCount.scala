package com.example.spark.streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * WordCount案例
  *
  * 监听端口采集数据
  * 从文件中采集数据
  */
object SparkStreamingWordCount {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming-WordCount")

    // 上下文环境对象
    val streamingContext: StreamingContext = new StreamingContext(sparkConf, Seconds(10))

    // 监控指定端口，获取数据
//    val textStream: ReceiverInputDStream[String] = streamingContext.socketTextStream("101.132.151.144", 8080)
    // 监控指定文件夹获取数据
    val textStream: DStream[String] = streamingContext.textFileStream("E:/Spark/streaming")


    // 数据处理装换
    val flatMap: DStream[String] = textStream.flatMap(line => line.split(" "))
    val map: DStream[(String, Int)] = flatMap.map((_, 1))
    val reduceByKey: DStream[(String, Int)] = map.reduceByKey(_ + _)

    // 显示结果
    reduceByKey.print()


    // 启动采集器
    streamingContext.start()
    // Driver等待采集器执行
    streamingContext.awaitTermination()
  }

}
