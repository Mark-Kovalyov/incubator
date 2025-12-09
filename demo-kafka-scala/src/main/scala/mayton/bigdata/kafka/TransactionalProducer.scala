package mayton.bigdata.kafka

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.header.Header
import org.apache.kafka.common.header.internals.RecordHeader

import java.util
import java.util.{Properties, UUID}
import scala.Seq
import scala.collection.JavaConverters.seqAsJavaListConverter
import scala.runtime.ObjectRef

/**
 * producer.initTransactions();
 * producer.beginTransaction();
 * producer.send(record);
 * producer.commitTransaction();
 */
object TransactionalProducer {

  def main(args: Array[String]): Unit = {

    println("DemoKafka is running...")

    val topic = "dhtpart"

    val props = new Properties()

    props.put("acks", "all")
    props.put("enable.idempotence", "true")
    props.put("transaction.timeout.ms", "60000")
    props.put("transactional.id", "my-transactional-producer-1")

    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer",    "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",  "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer(props)

    val callback = new XCallback()

    val r = new scala.util.Random

    producer.initTransactions()
    producer.beginTransaction()
    val batch_id = UUID.randomUUID.toString
    for (i <- 0 until 100) {
      val key = UUID.randomUUID.toString
      val value : Int = (30 * r.nextGaussian).asInstanceOf[Int]
      val partition = Math.abs(value % 4)
      val iterable : java.lang.Iterable[Header] = (List(new RecordHeader("batch_id", batch_id.getBytes()))).asJava
      val kafkaRecord = new ProducerRecord[Object, Object](
        topic,
        partition,
        System.currentTimeMillis,
        key,
        value.toString,
        iterable
      )
      producer.send(kafkaRecord, callback)
    }
    producer.commitTransaction()


    producer.close()

  }

}
