package com.example.scala.demo11.spark.common

// worker注册信息 //MessageProtocol.scala
case class RegisterWorkerInfo(id: String, cpu: Int, ram: Int)

// 这个是WorkerInfo, 这个信息将来是保存到master的 hm(该hashmap是用于管理worker)
// 将来这个WorkerInfo会扩展（比如增加worker上一次的心跳时间）
class WorkerInfo(val id: String, val cpu: Int, val ram: Int){
  var lastHeartBeat:Long = System.currentTimeMillis()
}

// 当worker注册成功，服务器返回一个RegisteredWorkerInfo 对象
case object RegisteredWorkerInfo

// worker每隔一段时间给自己发送一个心跳消息
case object SendHeartBeat

// worker每隔一段时间发送给master的协议消息
case class HeartBeat(id: String)

//master给自己发送一个触发检查超时worker的信息
case object StartTimeOutWorker
// master给自己发送检查worker状态的协议消息
case object RemoveTimeOutWorker