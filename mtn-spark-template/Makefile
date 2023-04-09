install_dir="/utils"

build: clean spark-template java-template java-jdbc-template java-jdbc-hikari-template scala-template cosmos-db-template java-kafka-producer-template
	mvn test install dependency:copy-dependencies
	cp -f target/dependency/* ./bin/
	rsync src/main/resources/shell/* ./bin

clean:
	rm -fr bin/*
	mkdir -p bin/

java-kafka-producer-template:
	mvn clean package -DskipTests -Pjava-kafka-producer
	cp -f target/java-kafka-producer-template.jar ./bin/

cosmos-db-template:
	mvn clean package -DskipTests -Pcosmos
	cp -f target/cosmos-db-template.jar ./bin/

spark-template:
	mvn clean package -DskipTests -Pspark
	cp -f target/spark-template.jar ./bin/

java-template:
	mvn clean package -DskipTests -Pjava
	cp -f target/java-template.jar ./bin/

java-jdbc-template:
	mvn clean package -DskipTests -Pjava-jdbc
	cp -f target/java-jdbc-template.jar ./bin/

java-jdbc-hikari-template:
	mvn clean package -DskipTests -Pjava-jdbc-hikari
	cp -f target/java-jdbc-hikari-template.jar ./bin/

scala-template:
	mvn clean package -DskipTests -Pscala
	cp -f target/scala-template.jar ./bin/

install: build
	mkdir -p $(install_dir)/mtn-template
	cp -f ./bin/* $(install_dir)/mtn-template/


