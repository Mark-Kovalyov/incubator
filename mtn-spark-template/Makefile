USER_HOME="~"

install_dir="$(USER_HOME)/bigdata-utils"

template:
	mvn clean package -DskipTests -Ptemplate
	cp -f target/template.jar ./bin/

build: clean  java-kafka-producer-template
	mvn test install dependency:copy-dependencies
	cp -f target/dependency/* ./bin/
	rsync src/main/resources/shell/* ./bin

build-template: clean template
	mvn test install dependency:copy-dependencies
	cp -f target/dependency/* ./bin/
	rsync src/main/resources/shell/* ./bin

clean:
	rm -fr bin/*/
	mkdir -p bin/

install: build
	mkdir -p $(install_dir)/mtn-template
	cp -f ./bin/* $(install_dir)/mtn-template/

install-template: build-template
	mkdir -p $(install_dir)/mtn-template
	cp -f ./bin/* $(install_dir)/mtn-template/
