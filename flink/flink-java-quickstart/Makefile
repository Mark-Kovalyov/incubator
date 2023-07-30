fraud:
	mvn clean package -Pfraud
	flink run --detached target/java-fraud-streaming-job.jar


udp:
	mvn clean package -Pudp
	flink run --detached target/java-udp-streaming-job.jar

kafka-udp:
	mvn clean package -Pkafka-udp
	flink run --detached target/java-kafka-udp-streaming-job.jar
