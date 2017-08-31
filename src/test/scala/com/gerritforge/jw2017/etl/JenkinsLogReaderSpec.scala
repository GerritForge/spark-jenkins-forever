package com.gerritforge.jw2017.etl

import java.io.FileWriter

import com.gerritforge.jw2017.model.{JenkinsLogMessage, TestDataFixture}
import org.scalatest.Inside

class JenkinsLogReaderSpec extends SparkEtlSpec with TestDataFixture with Inside {

  "A jenkins job JSON" should "be read into a DataSet" in withSpark { sc =>
    import sc.implicits._

    val jsonFile = java.io.File.createTempFile(System.getProperty("java.io.tmpdir"), "spark-")
    val writer = new FileWriter(jsonFile)
    writer.write(jobBuildLogRawJson.replace('\n', ' '))
    writer.close()

    val fileDs = sc.sqlContext.read.json(jsonFile.getAbsolutePath).as[JenkinsLogMessage]
    fileDs.count() should be(1)
    fileDs.collect() should contain (jenkinsLog)

  }
}
