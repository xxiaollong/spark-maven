package com.example.scala.demo10.crm.dao

import com.example.scala.demo10.crm.vo.Customer

import scala.collection.mutable.ListBuffer


/**
  * 客户管理数据层
  */
class CustomerDao {

}

object CustomerDao {
  var LISTS = ListBuffer[Customer](new Customer(1, "tom", 'x', 10, "119", "tom@qq.com"))

  def list(): ListBuffer[Customer] = {
    this.LISTS
  }

  def add(c: Customer): Boolean = {
    if (LISTS.isEmpty) {
      c.id = 1
    } else {
      c.id = LISTS.map(_.id).max + 1
    }
    this.LISTS += c
    true
  }

  def del(c: Customer): Boolean = {
    this.LISTS -= c
    true
  }

  def update(c: Customer): Boolean = {

    true
  }

  def findById(id: Int): Customer = {
    val list: ListBuffer[Customer] = this.LISTS.filter(_.id == id)
    if (list.size == 1) {
      list.head
    } else {
      null
    }
  }
}



