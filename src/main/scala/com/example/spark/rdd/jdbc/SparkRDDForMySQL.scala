package com.example.spark.rdd.jdbc

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * sparkRDD操作MySQL
  *
  */
object SparkRDDForMySQL {


  /**
    * JdbcRDD: 查询数据
    *
    * sc: SparkContext,   // SparkContext对象
    * getConnection: () => Connection,  // 一个JDBC连接
    * sql: String,    // 要执行的SQL
    * lowerBound: Long,   // 下界，表示第一个占位符的最小值
    * upperBound: Long,   // 上界，表示第二个占位符的最大值
    * numPartitions: Int, // 分区个数
    * mapRow: (ResultSet) => T = JdbcRDD.resultSetToObjectArray _)
    *
    */
  def my_JDBCRDD_Select(sc: SparkContext): Unit = {

    val jdbcRDD: JdbcRDD[(Int, String, Int)] = new JdbcRDD(
      sc,
      () => {
        DriverManager.getConnection("jdbc:mysql://localhost:3306/spark?characterEncoding=UTF-8&serverTimezone=UTC", "root", "root")
      },
      "select id, name, age from user where id>=? and id<=? ",
      1,
      100,
      1,
      res => {
        (res.getInt(1), res.getString(2), res.getInt(3))
      }
    )

    val array: Array[(Int, String, Int)] = jdbcRDD.collect()
    array.foreach(println)


  }

  /**
    * JDBCRDD: 保存数据(Driver 负责数据保存)
    *
    */
  def my_JDBCRDD_Save(sc: SparkContext): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    val connection: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spark?characterEncoding=UTF-8&serverTimezone=UTC", "root", "root")
    val sql: String = "insert into user(name, age) values (?, ?)"
    val statement: PreparedStatement = connection.prepareStatement(sql)

    val listRDD: RDD[(String, Int)] = sc.makeRDD(List(("白凤", 30), ("黑麒麟", 31)))
    listRDD.collect().foreach {
      case (name, age) => {
        statement.setString(1, name)
        statement.setInt(2, age)

        statement.executeLargeUpdate()
      }
    }

    connection.close()
  }

  /**
    * JDBCRDD: 保存数据(Executor 负责数据保存)
    *
    * 缺点：容易出行内存溢出
    */
  def my_JDBCRDD_Save2(sc: SparkContext): Unit = {
    val listRDD: RDD[(String, Int)] = sc.makeRDD(List(("白凤1", 30), ("黑麒麟1", 31), ("白凤2", 30), ("黑麒麟2", 31)))

    listRDD.foreachPartition(datas => {
      Class.forName("com.mysql.jdbc.Driver")
      val connection: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spark?characterEncoding=UTF-8&serverTimezone=UTC", "root", "root")
      val sql: String = "insert into user(name, age) values (?, ?)"
      val statement: PreparedStatement = connection.prepareStatement(sql)

      datas.foreach {
        case (name, age) => {
          statement.setString(1, name)
          statement.setInt(2, age)

          statement.executeLargeUpdate()
        }
      }

      connection.close()
    })

  }


  def main(args: Array[String]): Unit = {
    // 配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkRDDForMySQL")
    // 上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)


    my_JDBCRDD_Save2(sc)
    //    my_JDBCRDD_Save(sc)
    //    my_JDBCRDD_Select(sc)


    sc.stop()
  }


}
