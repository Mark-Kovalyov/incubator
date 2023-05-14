scalaVersion := "3.2.1"

organization := "mayton"
name         := "file-histogram"
version      := "1.0"

libraryDependencies ++= Seq(
   "org.scalatestplus" %% "scalacheck-1-17" % "3.2.15.0" % "test",
   "dev.zio"           %% "zio"             % "2.0.12"
)
