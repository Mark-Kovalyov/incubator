batch-job:
	mvn package -Pbatchjob
	flink run --detached target/batch-job.jar

streaming-job:
	mvn package -Pstreamingjob
	flink run --detached target/streaming-job.jar