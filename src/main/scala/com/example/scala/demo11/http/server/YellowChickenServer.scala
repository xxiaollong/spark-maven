package com.example.scala.demo11.http.server

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.example.scala.demo11.http.common.{ClientMessage, ServerMessage}
import com.typesafe.config.ConfigFactory


/**
  * 小黄鸡服务端
  */
class YellowChickenServer extends Actor{
  override def receive: Receive = {
    case "start" => println("小黄鸡服务端启动...")
    case ClientMessage(msg) => {
      msg match {
        case "学费" => sender() ! ServerMessage("35000RMB")
        case "地址" => sender() ! ServerMessage("北京")
        case _ => sender() ! ServerMessage("你说啥，听不懂")
      }
    }
  }
}

// 主程序
object YellowChickenServer{
  def main(args: Array[String]): Unit = {
    val host = "127.0.0.1" //服务端ip地址
    val port = 9999
    //创建config对象,指定协议类型，监听的ip和端口
    val config = ConfigFactory.parseString(
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname=$host
         |akka.remote.netty.tcp.port=$port
        """.stripMargin)

    // 创建ActorSystem
    val serverActorFactory = ActorSystem("server", config)
    // 创建YellowChickenServer的actor返回actorRef
    val actorRef: ActorRef = serverActorFactory.actorOf(Props[YellowChickenServer], "YellowChickenServer")

    // 启动
    actorRef ! "start"

  }

}