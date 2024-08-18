import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.2"

lazy val root = (project in file("."))
  .settings(
    resolvers ++= Seq(
      Resolver.mavenLocal,
      DefaultMavenRepository
    ),
    name := "MarkovDemo",
    libraryDependencies += "org.apache.commons" % "commons-compress" % "1.26.1",
    libraryDependencies += "mayton.libs" % "utils" % "1.13.0",
    libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.36",
    libraryDependencies += "org.slf4j" % "slf4j-ext" % "1.7.36",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.6",
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.13.2",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.18.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest"   % "3.2.18" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest-flatspec"     % "3.2.18" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest-funsuite"     % "3.2.18" % Test
)
