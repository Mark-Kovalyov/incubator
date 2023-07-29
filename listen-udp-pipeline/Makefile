ifeq ($(OS),Windows_NT)
	UDF_FILES=c:/udf/tmp
else
	UDF_FILES=/mnt/c/udf/tmp
endif

local:
	mvn clean package
	java -jar target/listen-udp-0.1.jar

remote:
	FLINK_CONF_DIR=c:/flink/
	mvn clean package
	$(FLINK_HOME)/bin/flink run --detached target/listen-udp-0.1.jar

