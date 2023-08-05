run:
	mvn clean package
	flink run  --detached target/flink-java-kafka-reader-1.0.jar

init-h2:
	mkdir -p /mnt/c/db/h2
	java -jar /mnt/c

init-sqlite:
	mkdir -p /mnt/c/db/sqlite
	sqlite3 --init sql/01.sql /mnt/c/db/sqlite/flink-java-kafka-reader.db

