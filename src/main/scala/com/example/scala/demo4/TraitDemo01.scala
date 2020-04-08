package com.example.scala.demo4

/**
  * trait : 特质，相当于java的interface(接口) + abstract(抽象类)
  *
  * 使用语法
  *   有父类：class 类名 extends 特质1 with 特质2 with 特质3...
  *   没有父类：class 类名 extends 父类 with 特质1 with 特质2 with 特质3...
  */
object TraitDemo01 {

}

// java中的所有接口都可以当做trait使用
// trait Serializable extends Any with java.io.Serializable
trait T001 extends Serializable {
}
// trait Cloneable extends java.lang.Cloneable
trait T002 extends Cloneable {
}