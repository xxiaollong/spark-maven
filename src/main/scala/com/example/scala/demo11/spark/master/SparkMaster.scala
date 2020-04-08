package com.example.scala.demo11.spark.master

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.example.scala.demo11.spark.common.{StartTimeOutWorker, _}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import scala.collection.mutable

/**
  *
  *
  */
class SparkMaster extends Actor {
  val workers = mutable.Map[String, WorkerInfo]()

  override def receive: Receive = {
    case "start" => {
      println("SparkMaster启动了")
      self ! StartTimeOutWorker
    }
    case RegisterWorkerInfo(id, cpu, ram) => {
      if (!workers.contains(id)) {
        val info: WorkerInfo = new WorkerInfo(id, cpu, ram)
        //        workers.put(id, info)
        workers += (id -> info)
        println("master: workers = " + workers)
        sender() ! RegisteredWorkerInfo
      }
    }
    // 接受worker心跳信息
    case HeartBeat(id) => {
      val workerInfo: WorkerInfo = workers(id)
      workerInfo.lastHeartBeat = System.currentTimeMillis()
      println(s"更新了 $id 的心跳信息：${workerInfo.lastHeartBeat}")
    }
    case StartTimeOutWorker => {
      // 定义一个定时器，发送心跳消息给自己，启动检查wokers状态
      import context.dispatcher
      context.system.scheduler.schedule(0 millis, 9000 millis, self, RemoveTimeOutWorker)
    }
    // 检查workers中的worker状态，删除超时worker
    case RemoveTimeOutWorker => {
      val millis: Long = System.currentTimeMillis()
      workers.filter(millis - _._2.lastHeartBeat > 6000L).foreach(workers -= _._1)
      println(s"当前有 ${workers.size} 个worker存活")
    }
    case _ => ""
  }
}

object SparkMaster {
  def main(args: Array[String]): Unit = {
    val (workerHost, workerPort) = ("127.0.0.1", "9958")
    val config = ConfigFactory.parseString(
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname=${workerHost}
         |akka.remote.netty.tcp.port=${workerPort}
            """.stripMargin)

    val actorSystem: ActorSystem = ActorSystem("SparkMaster", config)
    val actorRef: ActorRef = actorSystem.actorOf(Props[SparkMaster], "SparkMaster")

    // 启动
    actorRef ! "start"
  }
}
