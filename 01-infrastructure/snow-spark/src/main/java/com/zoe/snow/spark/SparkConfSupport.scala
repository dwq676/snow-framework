package com.zoe.snow.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * SparkConfSupport
  *
  * @author Dai Wenqing
  */
class SparkConfSupport {
  def getSparkConf(): SparkConf = {
    System.setProperty("hadoop.home.dir", "D:\\running\\hadoop-2.6.0");
    val conf = new SparkConf().setAppName("snow.spark.test22").setMaster("spark://10.0.2.54:7077")
      .set("spark.executor.memory", "100m")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    //.set("spark.eventLog.enabled", "true");
    return conf;
  }
}
