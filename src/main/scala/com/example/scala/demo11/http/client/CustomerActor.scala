package com.example.scala.demo11.http.client

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.example.scala.demo11.http.common.{ClientMessage, ServerMessage}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

/**
  * 小黄鸡客户端
  */
class CustomerActor(serverHost: String, serverPort: Int) extends Actor{
  // 服务端actorRef
  var serverActorRef: ActorSelection = _

  override def preStart(): Unit = {
    println("preStart() 执行...")
    serverActorRef = context.actorSelection(s"akka.tcp://server@${serverHost}:${serverPort}/user/YellowChickenServer")
    println(serverActorRef)
  }


  override def receive: Receive = {
    case "start" => println("小黄鸡客户端启动...")
    case msg:String => {
      serverActorRef ! ClientMessage(msg)
    }
    case ServerMessage(msg) =>{
      println("小黄鸡客服：" + msg)
    }


  }
}

object CustomerActor extends App{
  val (clientHost, clientPort, serverHost, serverPort) = ("127.0.0.1", 9990, "127.0.0.1", 9999)
  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=$clientHost
       |akka.remote.netty.tcp.port=$clientPort
        """.stripMargin)

  // 创建ActorSystem
  val customerActorFactory = ActorSystem("customer", config)
  // 创建YellowChickenServer的actor返回actorRef
  val actorRef: ActorRef = customerActorFactory.actorOf(Props(new CustomerActor(serverHost, serverPort)), "CustomerActor")

  // 启动
  actorRef ! "start"

  while (true){
    println("请输入要咨询的问题：")
    val msg = StdIn.readLine()
    actorRef ! msg
  }
}