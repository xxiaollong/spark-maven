package com.example.scala.demo10.crm.vo

import com.example.scala.demo10.crm.service.CustomerService

import scala.io.StdIn


/**
  * 显示界面实体类
  */
class CRMView {
  // 获取service
  val service = new CustomerService()

  // 是否循环标示
  var flag: Boolean = true

  // 接受输入变量
  var order: Char = _

  def start(): Unit = {
    do {
      println("---------客户信息管理软件---------")
      println("           1 添加客户")
      println("           2 修改客户")
      println("           3 删除客户")
      println("           4 客户列表")
      println("           5 退   出")
      println()
      print("请选择(1-5): ")
      order = StdIn.readChar()

      // 选择按钮
      select(order)

    } while (flag)
  }

  private def select(order: Char): Unit = {
    order match {
      case '1' => add()
      case '2' => println("修改")
      case '3' => del()
      case '4' => list()
      case '5' => flag = false
    }
  }

  private def list(): Unit = {
    println()
    println("------------客户列表-----------")
    println("编号\t\t姓名\t\t性别\t\t年龄\t\t电话\t\t邮箱")
    val list = service.list()
    for (c <- list) {
      println(c.toString)
    }
    println()
  }

  private def add(): Unit = {
    println()
    println("------------添加客户-----------")
    println("姓名:")
    val name = StdIn.readLine()
    println("性别:")
    val sex = StdIn.readChar()
    println("年龄:")
    val age = StdIn.readInt()
    println("电话:")
    val tel = StdIn.readLine()
    println("邮箱:")
    val email = StdIn.readLine()
    val c = new Customer(0, name, sex, age, tel, email)

    val f = service.add(c)

    if (f) println("添加成功")
  }

  private def del(): Unit ={
    println()
    println("删除的用户ID为: ")
    val id: Int = StdIn.readInt()

    val f = service.del(id)
    if (f) println("删除成功") else println("未执行删除")

  }

}
