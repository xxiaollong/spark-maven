package com.example.scala.demo10.crm.vo

/**
  * 客户类
  *
  */
class Customer {
  // 属性
  var id: Int = _
  var name: String = _
  var sex: Char = _
  var age: Int = _
  var tel: String = _
  var email: String = _

  // 自定义一个辅助构造器
  def this(id: Int,name: String,sex: Char,age: Int,tel: String, email: String){
    // 调用主构造器
    this
    this.id = id
    this.name = name
    this.sex = sex
    this.age = age
    this.tel = tel
    this.email = email
  }


  override def toString = s"$id\t\t$name\t\t$sex\t\t$age\t\t$tel\t\t$email"
}
