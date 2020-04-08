package com.example.scala.demo11.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Actor给自己发送消息
  *
  */
class Actor2Actor extends Actor{
  override def receive: Receive = {
    case "hello" => println("收到hello，返回：hello too")
    case "ok" => println("收到ok，返回：ok too")
    case "exit" => {
      // 停止当前actorRef
      context.stop(self)
      // 退出actorSystem
      context.system.terminate()

      println("退出系统")
    }
    case _ => println("没有匹配到")
  }
}

object Actor2ActorDemo{
  // 用于创建Actor
  private val actorFatory = ActorSystem("actorFatory")

  // 创建一个Actor2Actor实例，同时返回一个Actor2ActorRef
  // Props[Actor2Actor] : 创建Actor2Actor实例，创建的示例被ActorSystem接管
  private val actor2ActorDemoRef: ActorRef = actorFatory.actorOf(Props[Actor2Actor], "actor2Actor")

  def main(args: Array[String]): Unit = {

    actor2ActorDemoRef ! "hello"
    actor2ActorDemoRef ! "ok"
    actor2ActorDemoRef ! "exit"
  }




}
