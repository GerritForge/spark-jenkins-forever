package com.gerritforge.jw2017.job


import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import com.gerritforge.jw2017.etl.Transformers._
import org.elasticsearch.spark._
import org.json4s.DefaultFormats

case class MainOptions(jenkinsInputDir: String = "",
                       outputDir: String = "")

object Main extends App with Job {

  new scopt.OptionParser[MainOptions]("scopt") {
    head("scopt", "3.x")

    opt[String]('j', "jenkins") required() action { (x, c) =>
      c.copy(jenkinsInputDir = x)
    } text "Jenkins input directory"

    opt[String]('o', "out") required() action { (x, c) =>
      c.copy(outputDir = x)
    } text "output directory"

  }.parse(args, MainOptions()) match {
    case Some(conf) =>
      val sparkConf = new SparkConf().setAppName("Gerrit Jenkins ETL")

      implicit val spark = SparkSession.builder()
        .appName("Gerrit Jenkins events ETL")
        .config(sparkConf)
        .getOrCreate()

      val inputRdd = loadTextFiles(conf.jenkinsInputDir)
      val logsFiles = run(inputRdd)

      logsFiles.foreach {
        case (filePath, ds) => ds.rdd.saveAsTextFile(s"${conf.outputDir}/${filePath}")
      }
  }
}
