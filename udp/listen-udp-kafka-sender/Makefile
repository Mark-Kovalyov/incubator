build:
	mvn clean package

install: build
	cp -f target/*.jar ~/bin/listen-udp/

run: build
	java -jar target/udp-kafka-sender.jar localhost:9092 udp 0

