scalaVersion := "3.3.0"

organization := "mayton"
name         := "demo-dns"
version      := "1.0"

resolvers ++= Seq(
   Resolver.mavenLocal,
   DefaultMavenRepository
)

libraryDependencies ++= Seq(
   "org.scalatestplus" %% "scalacheck-1-17"   % "3.2.15.0" % "test",
   "dev.zio"           %% "zio"               % "2.0.15",
   "dev.zio"           %% "zio-streams"       % "2.0.15",

   "mayton.libs"          %  "network"        % "1.6.0-SNAPSHOT",
   "mayton.libs"          %  "utils"          % "1.10.1",
   "org.apache.commons"   % "commons-csv"     % "1.8",
   "com.google.code.gson" % "gson"            % "2.9.1",
   "com.github.jsurfer"   % "jsurfer-gson"    %   "1.6.4",
   "com.github.jsurfer"   % "jsurfer-jackson" %   "1.6.4"

)
