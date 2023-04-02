val scala3Version = "3.2.1"

scalacOptions ++= Seq("-explain")

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.mavenCentral
  )

Compile / packageBin / packageOptions += Package.ManifestAttributes("Class-Path" -> List().mkString(" "))

lazy val root = project
  .in(file("."))
  .settings(
    exportJars := true,
    name := "trigram",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-ext" % "2.0.3",
      "org.apache.commons" % "commons-csv" % "1.9.0",
      "commons-codec" % "commons-codec" % "1.15",
      "commons-io" % "commons-io" % "2.11.0",
      "ch.qos.logback" % "logback-classic" % "1.4.4",
      "com.google.guava" % "guava" % "31.1-jre"
      //"org.apache.spark" %% "spark-core" % "3.2.1",
      //"org.apache.spark" %% "spark-sql" % "3.2.1",
      //"org.apache.spark" %% "spark-mllib" % "3.2.1",
      //"com.databricks" %% "dbutils-api" % "0.0.6"
    )
  )


