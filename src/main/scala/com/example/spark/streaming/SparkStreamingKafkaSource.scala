package com.example.spark.streaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * 从Kafka中获取数据
  *
  * WordCount案例
  *
  */
object SparkStreamingKafkaSource {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming-Kafka")

    // 上下文环境对象
    val streamingContext: StreamingContext = new StreamingContext(sparkConf, Seconds(10))

    // 状态计算时需要设置加查点路径
    streamingContext.checkpoint("checkPoint")

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

    // 无状态计算
    val map: DStream[(String, Int)] = flatMap.map((_, 1))
    val reduceByKey: DStream[(String, Int)] = map.reduceByKey(_ + _)
    reduceByKey.print()

    // 有状态计算
    val updateStateByKey: DStream[(String, Int)] = map.updateStateByKey {
      case (seq, buffer) => {
        val sum: Int = buffer.getOrElse(0) + seq.sum
        Option(sum)
      }
    }
    updateStateByKey.print()

    // 启动采集器
    streamingContext.start()
    // Driver等待采集器执行
    streamingContext.awaitTermination()
  }

}
