package ca.farmersedge.blade.core

import org.apache.spark.SparkConf
import org.apache.spark.sql._

object SparkSessionFactory {

  def create(appName: String, master: String): SparkSession = {

    val conf = new SparkConf()
      .setAppName(appName)
      .setMaster(master)

    val spark =
      SparkSession
        .builder()
        .config(conf)
        .getOrCreate()

    spark
  }
}