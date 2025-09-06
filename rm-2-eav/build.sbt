import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "rm-2-eav-scala",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.postgresql" % "postgresql" % "42.2.6",
    libraryDependencies += "commons-cli" % "commons-cli" % "1.4"
  )

mainClass in (Compile, run) := Some("mayton.eav.RmToEav")
