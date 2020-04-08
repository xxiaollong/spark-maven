package com.example.spark.streaming

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * 自定义采集器
  *
  * WordCount案例
  */
object SparkStreamingMyReceiver {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming-WordCount")

    // 上下文环境对象
    val streamingContext: StreamingContext = new StreamingContext(sparkConf, Seconds(10))

    // 监控指定端口，获取数据
    val textStream: ReceiverInputDStream[String] = streamingContext.receiverStream(new MyReceiver("101.132.151.144",8080))

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

/**
  * 自定义采集器
  */
class MyReceiver(host:String, port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){
  var socket: Socket = null

  // 启动采集器
  override def onStart(): Unit = {
    socket = new Socket(host, port)
    val reader: BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream, "UTF-8"))

    var line:String = null
    while ((line = reader.readLine()) != null){
      if ("END".equals(line)){
        return
      }else{
        this.store(line)
      }
    }
  }
  // 停止采集器
  override def onStop(): Unit = {
    if (socket != null){
      socket.close()
      socket = null
    }
  }
}