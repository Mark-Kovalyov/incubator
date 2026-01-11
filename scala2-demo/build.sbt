ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .settings(
    name := "scala2-demo",
    idePackagePrefix := Some("mayton"),
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "4.1.1" % "provided"
    )
  )
