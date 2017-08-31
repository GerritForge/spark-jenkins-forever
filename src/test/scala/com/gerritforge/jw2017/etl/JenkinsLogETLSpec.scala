package com.gerritforge.jw2017.etl

import java.io.FileWriter

import com.gerritforge.jw2017.job.Job
import com.gerritforge.jw2017.model.{JenkinsLogData, JenkinsLogMessage, TestDataFixture}
import org.apache.spark.sql.Dataset
import org.scalatest.Inside

class JenkinsLogETLSpec extends SparkEtlSpec with TestDataFixture with Inside with Job {

  "A jenkins job JSON" should "be read into a DataSet" in withSpark { implicit sc =>
    import sc.implicits._
    import Readers._

    val rawJsonRdd = sc.sparkContext.parallelize(Seq(jobBuildLogRawJson))
    val logsDs = rawJsonRdd.jsonToDataframe.as[JenkinsLogMessage]

    logsDs.count() should be(1)
    logsDs.collect() should contain(jenkinsLog)
  }

  "A Dataset of JenkinsLogMessage" should
    "extract the list of log paths" in withSpark { implicit sc =>
    import sc.implicits._
    import Transformers._

    val logsDs: Dataset[JenkinsLogMessage] = sc.sparkContext.parallelize(
      Seq(jenkinsLog.copy(message = Seq("foo")), jenkinsLog.copy(message = Seq("bar")))).toDS
    val logPaths = logsDs.getLogPaths.collect()

    logPaths should have size 1
    logPaths.head should be(jenkinsLog.data.url)
  }

  it should "extract the ordered log messages of a path" in withSpark { implicit sc =>
    import sc.implicits._
    import Transformers._

    val logsDs: Dataset[JenkinsLogMessage] = sc.sparkContext.parallelize(
      Seq(jenkinsLog.copy(timestamp = "2017-01-02", data = JenkinsLogData(0, "path1"), message = Seq("bar")),
        jenkinsLog.copy(timestamp = "2017-01-01", data = JenkinsLogData(0, "path1"), message = Seq("foo"))
      )).toDS

    val logMessages = logsDs.getLogMessages("path1").collect()

    logMessages should have size 2
    logMessages should contain inOrderOnly("foo", "bar")
  }

  "ETL Job" should "extract the list of logs Datasets" in withSpark { implicit sc =>

    import sc.implicits._
    import Transformers._

    val rawJsonRdd = sc.sparkContext.parallelize(Seq(jobBuildLogRawJson))

    val logsDs = run(rawJsonRdd)

    logsDs should have size 1
    logsDs.keys should contain ("job/pipeline/job/33%252F33%252F4/1/")
    logsDs.values.head.collect should contain inOrderOnly("Building", " > git rev-parse --is-inside-work-tree # timeout=10")
  }
}
