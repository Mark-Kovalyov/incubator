import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.{Logger, LoggerFactory}
import RtrKafkaClient.{producer, producerCallback}

object RtrKafkaClient {

  val logger : Logger = LoggerFactory.getLogger("KafkaClient")

  val properties = new Properties()
  properties.put("bootstrap.servers", "localhost:9092")
  properties.put("key.serializer",   "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("value.serializer", "org.springframework.kafka.support.serializer.JsonSerializer")
  properties.put("client.id", "torrents")
  properties.put("acks", "0")
  properties.put("retries", "0")

  val producer: KafkaProducer[String, TorrentInfo] = new KafkaProducer(properties)

  val producerCallback: Callback = (metadata: RecordMetadata, exception: Exception) => {
    if (exception != null) {
      logger.error("Error when ProducerCallback::onCompletion", exception)
    }
  }

}


class RtrKafkaClient extends AbstractMQClient[TorrentInfo] {

  @throws(classOf[Exception])
  override def send(entity: TorrentInfo): Unit = {
    val kafkaRecord = new ProducerRecord[String, TorrentInfo]("topik1", "1", entity)
    producer.send(kafkaRecord, producerCallback)
  }

}
