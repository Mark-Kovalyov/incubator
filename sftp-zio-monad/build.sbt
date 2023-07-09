scalaVersion := "3.3.0"

organization := "mayton.sftp"
name         := "sftp-zio-monad"
version      := "1.0"



// "dev.zio" "zio" "2.0.15"

libraryDependencies ++= Seq(
   "org.scalatestplus" %% "scalacheck-1-17" % "3.2.15.0" % "test",
   "dev.zio"           %% "zio"             % "2.0.15",
   "dev.zio"           %% "zio-streams"     % "2.0.15",
   "dev.zio"           %% "zio-json"        % "0.5.0",
   "org.slf4j"         %  "slf4j-api"       % "2.0.7",
   "ch.qos.logback"    %  "logback-classic"  % "1.4.8",
   "org.postgresql"    %  "postgresql"       % "42.5.0",
   "dnsjava"           %  "dnsjava"          % "3.5.2"
)
