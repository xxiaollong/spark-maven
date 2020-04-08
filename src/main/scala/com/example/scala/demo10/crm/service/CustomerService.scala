package com.example.scala.demo10.crm.service

import com.example.scala.demo10.crm.dao.CustomerDao
import com.example.scala.demo10.crm.vo.Customer

import scala.collection.mutable.ListBuffer


/**
  * 客户管理
  */
class CustomerService {

  def list(): ListBuffer[Customer] ={
    CustomerDao.list()
  }

  def add(c: Customer):Boolean = {
    CustomerDao.add(c)
  }

  def del(id: Int): Boolean ={
    val c = CustomerDao.findById(id)
    if (c != null){
      CustomerDao.del(c)
      true
    }else{
      false
    }
  }

  def update(c: Customer): Boolean ={
    CustomerDao.update(c)
  }

}

