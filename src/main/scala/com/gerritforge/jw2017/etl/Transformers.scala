package com.gerritforge.jw2017.etl

import com.gerritforge.jw2017.model.JenkinsLogMessage
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object Transformers {

  implicit class PimpedJenkinsLogDs(val ds: Dataset[JenkinsLogMessage]) {

    def getLogPaths()(implicit sc: SparkSession): Dataset[String] = {
      import sc.implicits._

      ds.select(col("data.url").as[String]).distinct
    }

    def getLogMessages(path: String)(implicit sc: SparkSession): Dataset[String] = {
      import sc.implicits._

      ds.filter(_.data.url == path).sort(col("timestamp")).flatMap(_.message)
    }
  }


//  implicit class PimpedEventRdd(val rdd: RDD[Event]) {
//
//    def toJson: RDD[String] = {
//      rdd.map(write(_)(DefaultFormats))
//    }
//
//    def calculateDurations()(implicit sc: SparkSession): Dataset[Event] = {
//      import sc.sqlContext.implicits._
//
//      rdd.toDF
//        .withColumn("duration",
//          col("epoch") - lag(col("epoch"), 1)
//            .over(Window
//              .partitionBy("changeNum")
//              .orderBy("epoch"))).as[Event]
//    }
//
//    def filterChanges: RDD[Event] = rdd.filter(_.changeNum.isDefined)
//  }
}
