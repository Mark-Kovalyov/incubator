import Constants.JDBC_URL

import scala.jdk.CollectionConverters.*
import scala.jdk.StreamConverters.*
import java.time.Duration
import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.UUIDDeserializer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.slf4j.{Logger, LoggerFactory}
import org.apache.kafka.clients.consumer.ConsumerRecord

import java.sql.{Connection, DriverManager}

object KafkaPgPipeline {

  val logger: Logger = LoggerFactory.getLogger("KafkaPgPipeline")

  def main(args: Array[String]): Unit = {

    val INSERT_SQL_TEXT =
      """
        |INSERT INTO torrent_info(id,btih,name,published,page) VALUES (?,?,?,?,?)
        |ON CONFLICT ON CONSTRAINT torrent_info_id
        |DO NOTHING;
        |""".stripMargin

    val user = System.getenv("DB_USER")
    val pwd = System.getenv("DB_PWD")
    val properties = new Properties()
    properties.put("bootstrap.servers", "localhost:9092")
    properties.put("key.deserializer", classOf[UUIDDeserializer])
    properties.put("value.deserializer", classOf[StringDeserializer])
    properties.put("enable.auto.commit", "false")
    properties.put("group.id", "gr01")
    properties.put("consumer.id", java.util.UUID.randomUUID().toString)
    val consumer: KafkaConsumer[String, TorrentInfo] = new KafkaConsumer[String, TorrentInfo](properties)
    consumer.subscribe(List("torrents").asJava)
    val connection: Connection = DriverManager.getConnection(JDBC_URL, user, pwd)
    connection.setAutoCommit(false)
    var isWorking: Boolean = true
    val pst = connection.prepareStatement("")
    while (isWorking) {
      val records: Iterable[ConsumerRecord[String, TorrentInfo]] = consumer.poll(Duration.ofSeconds(15)).asScala
      for (record <- records) {
        
      }
      connection.commit()
      consumer.commitSync
    }

  }
}

