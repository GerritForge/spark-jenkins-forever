package com.gerritforge.jw2017.etl

import java.time.LocalDateTime

import com.gerritforge.jw2017.model.JenkinsLogMessage
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, Row, SparkSession}


object Readers {

  implicit def dateTimeOrdering: Ordering[LocalDateTime] = Ordering.fromLessThan(_ isBefore _)

  implicit class PimpedRowJSONRdd(val rawJsonRDD: RDD[String]) extends AnyVal {

    def jsonToDataframe()(implicit sc: SparkSession): Dataset[Row] = {
      import sc.implicits._

      val inputRawJsonRdd = rawJsonRDD.map(_.replaceAll("@timestamp", "timestamp"))
      sc.sqlContext.read.json(inputRawJsonRdd)
    }
  }

}
