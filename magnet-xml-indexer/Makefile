CURRENT_MAVEN_REPO=$(HOME)/.m2/repository

run:
	mvn -Dmaven.repo.local=$(CURRENT_MAVEN_REPO) compile exec:java -Dexec.mainClass="mayton.bigdata.MagnetXmlIndexer" -Dexec.args="/bigdata/documents documents"

run-release: package
	java -jar bin/magnet-xml-indexer.jar /mnt/c/books

package: clean
	mkdir -p bin
	mvn -Dmaven.repo.local=$(CURRENT_MAVEN_REPO) package install dependency:copy-dependencies
	cp -f target/*.jar ./bin
	cp -f target/dependency/*.jar ./bin

clean:
	mvn clean
	rm -f bin/*
