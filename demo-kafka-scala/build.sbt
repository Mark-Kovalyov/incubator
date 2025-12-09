ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .settings(
    resolvers ++= Seq(
      DefaultMavenRepository,
      "Clojars" at "https://repo.clojars.org/",
      Resolver.mavenLocal
    ),
    name := "demo-kafka-scala",
    libraryDependencies ++= Seq(
      //
      "org.apache.kafka" % "kafka-clients" % "4.1.1",
      "ch.qos.logback" % "logback-classic" % "1.5.21",
      "com.google.code.gson" % "gson" % "2.13.2",
      "com.google.protobuf" % "protobuf-java" % "4.33.1",
      "commons-codec" % "commons-codec" % "1.20.0",
      "org.apache.avro" % "avro" % "1.12.1"
    )
  )
