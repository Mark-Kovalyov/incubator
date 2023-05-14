val scala3Version = "3.2.1"

scalacOptions ++= Seq("-explain")

packageOptions += Package.ManifestAttributes(
  "Class-Path" -> "lib/scala-library-2.13.8.jar lib/scala3-library_3-3.1.2.jar lib/upickle_3-2.0.0.jar lib/ujson_3-2.0.0.jar lib/geny_3-0.7.1.jar lib/commons-csv-1.9.0.jar"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "scottgen",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.apache.commons" % "commons-csv" % "1.9.0",
      "com.lihaoyi" %% "upickle" % "2.0.0",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.13.4",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.4"
    )
  )
