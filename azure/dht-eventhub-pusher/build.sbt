import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.5-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.1"

scalacOptions ++= Seq("-explain")

val SCALATEST_VERSION = "3.2.19"
val ZIO_VERSION       = "2.1.9"
val SLF4J_VERSION     = "1.7.36"

ThisBuild / publishMavenStyle := true

lazy val root = (project in file("."))
  .settings(
    resolvers ++= Seq(
      Resolver.mavenLocal,
      Resolver.url("https://maven.aksw.org/repository/internal/"),
      DefaultMavenRepository
    ),
    name := "dht-evenhub-pusher",
    libraryDependencies ++= Seq(
      "com.azure" % "azure-messaging-eventhubs" % "5.20.4",
      "com.azure" % "azure-identity" % "1.16.1",
      "org.slf4j"  % "slf4j-api" % "1.7.36",
      "org.slf4j" % "slf4j-ext" % "1.7.36",
      "ch.qos.logback" % "logback-classic" % "1.5.6"
    )
  )

