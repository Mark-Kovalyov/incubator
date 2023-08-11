string-consumer:
	mvn compile exec:java -Dexec.mainClass=mayton.kafka.StringConsumer -Dexec.args="--topic udp --bootstrap-servers localhost:9092 --consumer-id cons001 --group-id group001"

prime-producer:
	mvn compile exec:java -Dexec.mainClass=mayton.kafka.PrimeProducer  -Dexec.args="--topic primes --bootstrap-servers localhost:9092"

