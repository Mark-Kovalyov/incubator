#!/bin/bash -v

set -e

sbt "clean; package"

mkdir -p lib

rsync target/scala-3.1.2/scottgen_3-0.1.0-SNAPSHOT.jar   ./scottgen_3-0.1.0-SNAPSHOT.jar
rsync "$SCALA_HOME/lib/scala-library-2.13.8.jar"         lib/scala-library-2.13.8.jar
rsync "$SCALA_HOME/lib/scala3-library_3-3.1.2.jar"       lib/scala3-library_3-3.1.2.jar

wget -nc https://repo1.maven.org/maven2/com/lihaoyi/upickle_3/2.0.0/upickle_3-2.0.0.jar
wget -nc https://repo1.maven.org/maven2/com/lihaoyi/ujson_3/2.0.0/ujson_3-2.0.0.jar
wget -nc https://repo1.maven.org/maven2/com/lihaoyi/geny_3/0.7.1/geny_3-0.7.1.jar
wget -nc https://repo1.maven.org/maven2/org/apache/commons/commons-csv/1.9.0/commons-csv-1.9.0.jar
wget -nc https://repo1.maven.org/maven2/com/lihaoyi/upack_3/2.0.0/upack_3-2.0.0.jar
wget -nc https://repo1.maven.org/maven2/com/lihaoyi/upickle-implicits_3/2.0.0/upickle-implicits_3-2.0.0.jar

rsync upickle_3-2.0.0.jar lib/upickle_3-2.0.0.jar
rsync ujson_3-2.0.0.jar   lib/ujson_3-2.0.0.jar
rsync geny_3-0.7.1.jar    lib/geny_3-0.7.1.jar
rsync commons-csv-1.9.0.jar lib/commons-csv-1.9.0.jar
rsync upack_3-2.0.0.jar lib/upack_3-2.0.0.jar
rsync upickle-implicits_3-2.0.0.jar lib/upickle-implicits_3-2.0.0.jar

java -jar scottgen_3-0.1.0-SNAPSHOT.jar


