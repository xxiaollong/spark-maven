package com.example.scala.demo11.spark.worker

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.example.scala.demo11.spark.common._
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

/**
  * Created by admin on 2019-10-24.
  */
class SparkWorker(masterHost: String, masterPort: Int, masterName: String) extends Actor {
  var masterPorxy: ActorSelection = _
  val id = java.util.UUID.randomUUID().toString

  override def preStart(): Unit = {
    masterPorxy = context.actorSelection(s"akka.tcp://SparkMaster@$masterHost:$masterPort/user/$masterName")
    println(masterPorxy)
  }

  override def receive: Receive = {
    case "start" => {
      println("SparkWorker启动了")
      masterPorxy ! RegisterWorkerInfo(id,16, 16*1024)
    }
    case RegisteredWorkerInfo =>{
      println(s"worker： id=$id 注册成功")

      // 定义一个定时器，发送心跳消息给自己
      import context.dispatcher
      // 0 millis: 延迟多上时间开始执行
      // 3000 millis: 每隔多长时间执行一次
      // self: 发送给谁
      // SendHeartBeat: 发送的内容
      context.system.scheduler.schedule(0 millis, 3000 millis, self, SendHeartBeat)
    }

    // 向master发送心跳信息
    case SendHeartBeat => {
      masterPorxy ! HeartBeat(id)
    }
    case _ => ""
  }
}

object SparkWorker {
  def main(args: Array[String]): Unit = {
    val workerHost: String = "127.0.0.1"
    val workerPort: Int = 10007
    val masterHost: String = "127.0.0.1"
    val masterPort: Int = 9958
    val masterName: String = "SparkMaster"

    val config = ConfigFactory.parseString(
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname=${workerHost}
         |akka.remote.netty.tcp.port=${workerPort}
            """.stripMargin)

    val actorSystem: ActorSystem = ActorSystem("SparkWorker", config)
    val actorRef: ActorRef = actorSystem.actorOf(
      Props(new SparkWorker(masterHost, masterPort, masterName)), "SparkWorker-01")

    // 启动
    actorRef ! "start"
  }
}
