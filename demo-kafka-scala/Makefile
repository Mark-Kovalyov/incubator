simple:
	sbt "runMain mayton.bigdata.kafka.SimpleProducer"

transactional:
	sbt "runMain mayton.bigdata.kafka.TransactionalProducer"

protobuf:
	protoc --java_out=src/main/java/ src/main/resources/geo-ip-record.proto

avro:
	java -jar avro-tools-1.12.1.jar compile schema src/main/resources/emp.avsc src/main/java/

thrift:
	thrift --gen java -out src/main/java/ src/main/resources/demo.thrift