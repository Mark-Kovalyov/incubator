import scala.{Seq, collection}

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.3"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-lang3" % "3.14.0",
  "org.scalatest" %% "scalatest" % "3.2.17" % Test,
  "org.scalatest" %% "scalatest-funsuite" % "3.2.17" % "test",
  "org.scalacheck" %% "scalacheck" % "1.17.0" % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "demo-decryptor",
    idePackagePrefix := Some("mayton.crypto")
  )
