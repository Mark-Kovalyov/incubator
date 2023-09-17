package: clean
	mvn package
	mvn install dependency:copy-dependencies
	mkdir -p bin
	cp -f target/diff-doc.jar bin/
	cp -f src/main/resources/diff-doc bin/
	rsync target/dependency/* ./bin

graal: package
	JAVA_HOME=/graalvm/17.0.8 PATH=$(JAVA_HOME)/bin CLASSPATH=$(JAVA_HOME)/lib && ./native-image diff-doc.jar

hot-run:
	mvn clean compile exec:java -Dexec.mainClass=mayton.tools.DiffDoc -Dexec.args="/bigdata/documents /bigdata/documents.old /bigdata/tmp/001 ps"

full-run: package
	cd bin && java -jar diff-doc.jar /bigdata/documents /bigdata/documents.old /bigdata/tmp/001 "djvu"

clean:
	mvn clean
	rm -fr bin/

