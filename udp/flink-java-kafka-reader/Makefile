run:
	mvn clean package -Pkafkareader
	flink run --detached target/kafkareader.jar

dummy:
	mvn clean package -Pdummy
	flink run --detached target/dummy.jar

init-h2:
	mkdir -p /mnt/c/db/h2
	java -jar /mnt/c

init-sqlite:
	mkdir -p /bigdata/sqlite/flink-java-kafka-reader/
	sqlite3 --init sql/01.sql /bigdata/sqlite/flink-java-kafka-reader/flink-java-kafka-reader.db

init-pg:
	psql -d dht
