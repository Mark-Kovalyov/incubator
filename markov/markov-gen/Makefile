SCALA_VERSION=3.7.2

SCALA2_VERSION=2.13.16

GRAALVM_HOME=/graalvm

SCALA3_HOME=/mnt/c/scala/$(SCALA_VERSION)

MVN_REP=$(HOME)/.m2/repository

#TAG=russian-mat
#FILE=words.txt.bz2
#ENCODING=utf-8

#FILE=forum-names.csv.bz2
#TAG=forumnames
#ENCODING=UTF-8

TAG=rockyou
FILE=rockyou-train.csv.bz2
ENCODING=ISO_8859_1

#TAG=onelist-all-symbols
#FILE=one-list.csv.bz2
#ENCODING=utf-8

#TAG=elvens
#FILE=elvens.csv.bz2
#ENCODING=utf-8

#TAG=onelist-for-all-symbols
#FILE=onelistforall.csv.bz2
#ENCODING=ISO_8859_1

run:
	sbt "runMain mayton.MarkovPwdBatchRunner $(TAG) data/$(FILE) $(ENCODING) data . 50000 20"

run-jar: package
	java -jar bin/markovdemo_3-0.1.0-SNAPSHOT.jar $(TAG) data/$(FILE) $(ENCODING) data . 50000 20

run-native: build-native
	./bin/mayton.markovpwdbatchrunner $(TAG) data/$(FILE) $(ENCODING) data . 50000 20

build-native: package
	conda activate graalvm
	native-image --no-fallback -J-Xmx3072m -cp markovdemo_3-0.1.0-SNAPSHOT.jar mayton.MarkovPwdBatchRunner
	conda deactivate

package:
	mkdir -p bin/
	sbt clean test package
	cp -f target/scala-$(SCALA_VERSION)/markovdemo_3-0.1.0-SNAPSHOT.jar bin/
	cp -f $(MVN_REP)/mayton/libs/utils/2.3.1-SNAPSHOT/utils-2.3.1-SNAPSHOT.jar bin/
	cp -f $(MVN_REP)/commons-io/commons-io/2.8.0/commons-io-2.8.0.jar bin/
	cp -f $(MVN_REP)/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar bin/
	cp -f $(MVN_REP)/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar bin/
	cp -f $(MVN_REP)/org/slf4j/slf4j-simple/1.7.36/slf4j-simple-1.7.36.jar bin/
	cp -f $(MVN_REP)/org/slf4j/slf4j-ext/1.7.36/slf4j-ext-1.7.36.jar bin/
	cp -f $(MVN_REP)/org/apache/commons/commons-compress/1.26.2/commons-compress-1.26.2.jar bin/
	cp -f $(SCALA3_HOME)/maven2/org/scala-lang/scala-library/$(SCALA2_VERSION)/scala-library-$(SCALA2_VERSION).jar bin/
	cp -f $(SCALA3_HOME)/maven2/org/scala-lang/scala3-library_3/$(SCALA_VERSION)/scala3-library_3-$(SCALA_VERSION).jar bin/

run: graph-processor markov-hist-gen markov-pw-gen

graph-processor:
	sbt "runMain mayton.MarkovGraphGen $(TAG) data//$(FILE) $(ENCODING)"

markov-hist-gen:
	sbt "runMain mayton.MarkovHistGen $(TAG) data/stage-02 data/stage-03"

markov-pw-gen:
	sbt "runMain mayton.MarkovPwGen $(TAG) data/stage-03 data/stage-04 5000 16"

