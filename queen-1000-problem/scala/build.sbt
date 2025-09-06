name := "queen-problem-scala"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.17"

mainClass in (Compile, run) := Some("mayton.chess.Main")