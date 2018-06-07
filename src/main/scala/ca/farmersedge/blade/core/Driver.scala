package ca.farmersedge.blade.core

import ca.farmersedge.blade.core.elastic.ESClient
import com.typesafe.scalalogging.LazyLogging

object Driver extends LazyLogging {

  def main(args: Array[String]): Unit = {
    logger.info("Starting job tracker")

    ESClient.docid = "testHello"
    val spark = SparkSessionFactory.create("test", "local")


    Thread.sleep(3000)
    spark.close()

  }
}