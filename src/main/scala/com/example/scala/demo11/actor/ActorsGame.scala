package com.example.scala.demo11.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive

/**
  *
  * actor之间相互通信
  *
  */
object ActorsGame {
  private val actorFactory = ActorSystem("actorFactory")
  private val bRef = actorFactory.actorOf(Props[BActor], "b_actor")
  private val aRef = actorFactory.actorOf(Props(new AActor(bRef)), "a_actor")

  def main(args: Array[String]): Unit = {
    aRef ! "start"
  }

}

class AActor(actorRef: ActorRef) extends Actor{
  val bActorRef = actorRef
  var count = 0
  override def receive: Receive = {
    case "start" =>{
      println("AActor：比赛开始")
      // 发给自己
      self ! "我打"
    }
    case "我打" => {
      Thread.sleep(1000)
      println("AActor: 我照你脸打了一拳")
      bActorRef ! "我打"
      count += 1
      println("count : " + count)
      if (count == 10){
        bActorRef ! "stop"
        println("累了，休息休息")
        context.system.terminate()
      }
    }

    case _ => println("没有匹配")
  }
}

class BActor extends Actor{
  override def receive: Receive = {
    case "stop" =>{
      println("好的，休息休息")
      context.stop(self)
    }
    case "我打" => {
      Thread.sleep(1000)
      println("BActor: 我照你裆踢了一脚")
      sender() ! "我打"
    }
    case _ => println("没有匹配")
  }
}
