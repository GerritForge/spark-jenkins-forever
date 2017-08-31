package com.gerritforge.jw2017.job

import com.gerritforge.jw2017.model.JenkinsLogMessage
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

trait Job {

  def loadTextFiles(inputDir: String)(implicit sc: SparkSession): RDD[String] = {
    sc.sparkContext.textFile(s"$inputDir/*")
  }

  def run(rawJsonRdd: RDD[String])(implicit sc: SparkSession): Map[String,Dataset[String]] = {
    import com.gerritforge.jw2017.etl.Readers._
    import com.gerritforge.jw2017.etl.Transformers._
    import sc.implicits._

    val logs = rawJsonRdd.jsonToDataframe().as[JenkinsLogMessage]
    val paths = logs.getLogPaths().collect()
    paths.map(path => (path, logs.getLogMessages(path))).toMap
  }
}
