scalaVersion := "3.2.2"

organization := "mayton.bigdata"
name         := "${name}"
version      := "1.0"

libraryDependencies ++= Seq(
   "org.scalatestplus" %% "scalacheck-1-17" % "3.2.15.0" % "test",
   "org.yaml" % "snakeyaml" % "2.0"
)
