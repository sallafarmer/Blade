name := "Blade"

version := "1.0"

scalaVersion := "2.11.12"


scalacOptions ++= Seq(
  "-Xlint",
  "-explaintypes",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings"
)

val sparkVersion = "2.3.0"
val slf4jVersion = "1.7.25"
val logbackClassicVersion = "1.2.3"
val scalaLoggingVersion = "3.9.0"
val json4sVersion = "3.5.3"
val elasticVersion = "5.5.0"
val typesafeVersion = "1.3.3"

resolvers += "MavenRepository" at "https://mvnrepository.com/"

libraryDependencies ++= Seq(

  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,

  "org.slf4j" % "log4j-over-slf4j" % slf4jVersion,
  "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,

  "org.json4s" %% "json4s-native" % json4sVersion,
  "org.json4s" %% "json4s-jackson" % json4sVersion,

  "org.elasticsearch" % "elasticsearch" % elasticVersion,
  "org.elasticsearch.client" % "transport" % elasticVersion,

  "com.typesafe" % "config" % typesafeVersion

).map(_.exclude("org", "log4j")
  .exclude("org.slf4j", "slf4j-log4j12"))