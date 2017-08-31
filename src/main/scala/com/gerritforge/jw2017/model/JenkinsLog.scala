package com.gerritforge.jw2017.model

case class JenkinsLogMessage( timestamp: String,
                              data: JenkinsLogData,
                             message: Seq[String])

case class JenkinsLogData(buildNum: Long,
                          url: String)
