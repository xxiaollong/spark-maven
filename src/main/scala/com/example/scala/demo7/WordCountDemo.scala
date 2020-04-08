package com.example.scala.demo7

/**
  * Scala的WordCount案例
  */
object WordCountDemo {

  def main(args: Array[String]): Unit = {
    val list = List("AAA BBB", "AAA BBB CCC DDD CCC AAA ")

    // 使用切割符号将文本切割成单词，然后压平
    //    val res1 = list.flatMap((line:String) => line.split(" "))
    val res1 = list.flatMap(_.split(" "))
    println(res1)

    // 使用map将每个单词组装成次数为1的元组
    //    val res2 = res1.map((word:String) => (word, 1))
    val res2 = res1.map((_, 1))
    println(res2)

    // 使用元组的第一个元素分组，形成一个word->List[(word, 1)]的集合
    //    val res3 = res2.groupBy((x:(String,Int)) => x._1)
    val res3 = res2.groupBy(_._1)
    println(res3)

    // 遍历集合得到word->list.size的集合
    //    val res4 = res3.map((x:(String, List[(String, Int)])) => (x._1, x._2.size))
    val res4 = res3.map(x => (x._1, x._2.size))
    println(res4)

    // 将map装换为list，使用sortBy()方法排
    //    val res5 = res4.toList.sortBy((x:(String, Int)) => x._2).reverse
    val res5 = res4.toList.sortBy(_._2).reverse
    println(res5)


    //合并写法
    val res6 = list.flatMap(_.split(" ")).map((_, 1)).groupBy(_._1).map(x => (x._1, x._2.size)).toList.sortBy(_._2).reverse
    println(res6)


  }


}
