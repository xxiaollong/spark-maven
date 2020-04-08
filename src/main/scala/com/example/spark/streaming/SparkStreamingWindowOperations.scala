package com.example.spark.streaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}


/**
  * 窗口函数
  *
  * 注意：窗口大小和滑动步长都是采集周期的整数倍
  */
object SparkStreamingWindowOperations {


  Logger.getLogger("org").setLevel(Level.ERROR)

  /**
    * sliding(size: Int, step: Int): scala的窗口函数
    * size：窗口大小
    * step：窗口滑动步长
    */
  def window_scala(): Unit = {
    val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    // 滑动
    val sliding: Iterator[List[Int]] = list.sliding(3)
    for (elem <- sliding) {
      println(elem)
    }

    println("===================")
    val sliding1: Iterator[List[Int]] = list.sliding(3, 3)
    for (elem <- sliding1) {
      println(elem)
    }

  }

  def window_sparkStreaming(): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming-Window")

    // 采集周期
    val times: Int = 10

    // 上下文环境对象
    // Seconds(10): 表示采集周期为10秒
    val streamingContext: StreamingContext = new StreamingContext(sparkConf, Seconds(times))

    // kafka配置对象
    val kafkaParams: Map[String, Object] = Map(
      ("bootstrap.servers", "192.168.1.231:9096"),
      ("group.id", "test-consumer-group"),
      ("auto.offset.reset", "earliest"),
      ("key.deserializer", classOf[StringDeserializer]),
      ("value.deserializer", classOf[StringDeserializer])
    )
    val topics: Set[String] = Set("wxl_test")

    // 从kafka中获取数据
    val textStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    // 数据处理装换
    val flatMap: DStream[String] = textStream.flatMap(t => t.value().split(" "))
    val map: DStream[(String, Int)] = flatMap.map((_, 1))

    // 窗口大小和滑动步长都是采集周期的整数倍
    // Seconds(15), Seconds(5): 15秒为一个窗口，每5秒滑动一次
    val window: DStream[(String, Int)] = map.reduceByKeyAndWindow(
      (v1: Int, v2: Int) => v1 + v2,
      Seconds(times * 3),
      Seconds(times)
    )

    window.print()

    // 启动采集器
    streamingContext.start()
    // Driver等待采集器执行
    streamingContext.awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    window_sparkStreaming()
    //    window_scala()
  }


}
