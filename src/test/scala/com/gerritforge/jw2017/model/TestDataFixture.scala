package com.gerritforge.jw2017.model

trait TestDataFixture {

  val jobBuildLogRawJson =
    """
      |{
      |	"source_host": "http://jenkins-pipeline/",
      |	"@timestamp":"2017-08-31T00:36:23.000Z",
      |	"data": {
      |		"buildVariables": {
      |			"BUILD_TAG": "jenkins-pipeline-33%2F33%2F4-1",
      |			"RUN_DISPLAY_URL": "http://jenkins-pipeline/job/pipeline/job/33%252F33%252F4/1/display/redirect",
      |			"HUDSON_HOME": "/var/jenkins_home",
      |			"RUN_CHANGES_DISPLAY_URL": "http://jenkins-pipeline/job/pipeline/job/33%252F33%252F4/1/display/redirect?page=changes",
      |			"JOB_URL": "http://jenkins-pipeline/job/pipeline/job/33%252F33%252F4/",
      |			"HUDSON_SERVER_COOKIE": "d4835bf5e81a8af3",
      |			"JOB_BASE_NAME": "33%2F33%2F4",
      |			"BUILD_ID": "1",
      |			"CLASSPATH": "",
      |			"JENKINS_HOME": "/var/jenkins_home",
      |			"JOB_NAME": "pipeline/33%2F33%2F4",
      |			"BUILD_NUMBER": "1",
      |			"BUILD_DISPLAY_NAME": "#1",
      |			"BUILD_URL": "http://jenkins-pipeline/job/pipeline/job/33%252F33%252F4/1/",
      |			"HUDSON_URL": "http://jenkins-pipeline/",
      |			"JOB_DISPLAY_URL": "http://jenkins-pipeline/job/pipeline/job/33%252F33%252F4/display/redirect",
      |			"JENKINS_SERVER_COOKIE": "d4835bf5e81a8af3",
      |			"BRANCH_NAME": "33/33/4",
      |			"JENKINS_URL": "http://jenkins-pipeline/"
      |		},
      |		"buildNum": 1,
      |		"rootProjectName": "33%2F33%2F4",
      |		"rootProjectDisplayName": "#1",
      |		"displayName": "#1",
      |		"fullDisplayName": "pipeline » 33/33/4 #1",
      |		"rootBuildNum": 1,
      |		"id": "1",
      |		"projectName": "33%2F33%2F4",
      |		"url": "job/pipeline/job/33%252F33%252F4/1/",
      |		"buildDuration": 52418,
      |		"buildHost": "Executor #-1"
      |	},
      |	"@version": 1,
      |	"source": "jenkins",
      |	"message": ["Building", " > git rev-parse --is-inside-work-tree # timeout=10"]
      |}
    """.stripMargin

  val jenkinsLog = JenkinsLogMessage(
    timestamp = "2017-08-31T00:36:23.000Z",
    JenkinsLogData(1, "job/pipeline/job/33%252F33%252F4/1/"),
    Seq("Building", " > git rev-parse --is-inside-work-tree # timeout=10"))
}
