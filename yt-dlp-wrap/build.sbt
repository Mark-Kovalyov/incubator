scalaVersion := "3.9.0"

val SCALATEST_VERSION = "3.2.20"

lazy val root = rootProject
  .settings(
    name := "yt-dlp-wrap",
    idePackagePrefix := Some("enot"),
    libraryDependencies ++= Seq(
      // JSON
      "tools.jackson.core" % "jackson-databind" % "3.2.3",
      //
      "commons-io" % "commons-io" % "2.22.0",
      // Scalatest
      "org.scalatestplus" %% "scalacheck-1-18"    % "3.2.19.0" % Test,
      "org.scalacheck" %% "scalacheck"            % "1.19.0" % Test,
      "org.scalameta" %% "munit"                  % "1.3.4" % Test,
      "org.scalatest" %% "scalatest"              % SCALATEST_VERSION % Test,
      "org.scalatest" %% "scalatest-flatspec"     % SCALATEST_VERSION % Test,
      "org.scalatest" %% "scalatest-funsuite"     % SCALATEST_VERSION % Test,
      "org.slf4j" % "slf4j-simple" % "2.0.20",
      "org.slf4j" % "slf4j-api" % "2.0.20"
    )
  )
