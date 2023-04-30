scalaVersion := "3.2.2"

organization := "mayton.sftp"
name         := "sftp-zio-monad"
version      := "1.0"

libraryDependencies ++= Seq(
   "org.scalatestplus" %% "scalacheck-1-17" % "3.2.15.0" % "test",
   "dev.zio"           %% "zio"             % "2.0.13",
   "dev.zio"           %% "zio-streams"     % "2.0.13"
)
