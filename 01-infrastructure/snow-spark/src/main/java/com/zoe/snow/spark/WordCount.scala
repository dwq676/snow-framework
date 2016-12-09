package com.zoe.snow.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/**
  * HelloSpark
  *
  */
object WordCount extends Serializable {

  def main(args: Array[String]) {
    /*if (args.length < 1) {
      System.err.println("Usage: <file>")
      System.exit(1)
    }*/
    //System.setProperty("hadoop.home.dir", "D:\\running\\hadoop-2.6.0");
    val conf = new SparkConfSupport().getSparkConf();
    //.set("spark.eventLog.enabled", "true");
    val sc = new SparkContext(conf);

    val line = sc.textFile("hdfs://10.0.2.54:9000/linkage");

    //line.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).collect().foreach(println)

    line.take(10).foreach(println);

    sc.stop()
  }
}
