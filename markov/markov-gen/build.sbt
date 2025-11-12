import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.2"

val SCALATEST_VERSION = "3.2.19"
val ZIO_VERSION       = "2.1.9"
val SLF4J_VERSION     = "1.7.36"

Compile / packageBin / packageOptions +=
  Package.ManifestAttributes(
      "Class-Path" ->
        List(
            "scala3-library_3-3.7.2.jar",
            "scala-library-2.13.16.jar",
            "slf4j-api-1.7.36.jar",
            "slf4j-simple-1.7.36.jar",
            "commons-compress-1.26.2.jar",
            "utils-2.3.1-SNAPSHOT.jar",
            "slf4j-ext-1.7.36.jar",
            "commons-io-2.8.0.jar",
            "commons-lang3-3.9.jar"
        ).mkString(" "),
      "Main-Class" -> "mayton.MarkovPwdBatchRunner"
)

lazy val root = (project in file("."))
  .settings(
    exportJars := true,
    resolvers ++= Seq(
      Resolver.mavenLocal,
      DefaultMavenRepository
    ),
    name := "MarkovDemo",
        // Apache Commons Compress defines an API for working with compression and archive formats.
        // These include bzip2, gzip, pack200, LZMA, XZ, Snappy, traditional Unix Compress, DEFLATE,
        // DEFLATE64, LZ4, Brotli, Zstandard and ar, cpio, jar, tar, zip, dump, 7z, arj.
    libraryDependencies += "org.apache.commons" % "commons-compress" % "1.26.2",
    libraryDependencies += "mayton.libs" % "utils" % "2.3.1-SNAPSHOT",
    libraryDependencies += "org.slf4j" % "slf4j-api" % SLF4J_VERSION,
    libraryDependencies += "org.slf4j" % "slf4j-ext" % SLF4J_VERSION,
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.6",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.18.0" % Test,
    libraryDependencies += "org.scalatest"  %% "scalatest"              % SCALATEST_VERSION % Test,
    libraryDependencies += "org.scalatest"  %% "scalatest-flatspec"     % SCALATEST_VERSION % Test,
    libraryDependencies += "org.scalatest"  %% "scalatest-funsuite"     % SCALATEST_VERSION % Test
)
