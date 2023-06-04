val scala3Version = "3.1.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "rtr-parser",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    resolvers ++= Seq(MavenCache("local-maven", file("/home/mayton/.m2/repository"))),
    libraryDependencies ++= Seq(
      // Testing
      "org.scalameta"     %% "munit" % "0.7.29" % Test,
      "org.scalatestplus" %% "scalacheck-1-16" % "3.2.12.0" % Test,
      // Parsing
      "org.jsoup"         % "jsoup" % "1.15.2",
      "org.postgresql"    % "postgresql" % "42.4.1",
      "org.yaml"          % "snakeyaml" % "1.30",
      "com.google.guava"  % "guava" % "31.1-jre",
      // Logging
      "ch.qos.logback"    % "logback-classic" % "1.2.3",
      "org.slf4j"         % "slf4j-api" % "2.0.0",
      "mayton.libs"       % "utils"     % "1.10.0",
      // Kafka support
      "org.apache.kafka"  % "kafka-clients" % "3.2.1",
      "org.springframework.kafka" % "spring-kafka" % "2.9.0"
    )

  )
