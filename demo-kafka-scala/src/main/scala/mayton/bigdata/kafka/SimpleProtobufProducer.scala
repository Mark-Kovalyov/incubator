package mayton.bigdata.kafka

import com.google.gson.{Gson, JsonObject}
import mayton.bigdata.kafka.protobuf.GeoIpRecordOuterClass
import org.apache.commons.codec.binary.Hex
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.KafkaException
import org.apache.kafka.common.errors.ProducerFencedException

import java.util.concurrent.Future
import java.util.{Properties, UUID}
import scala.runtime.ObjectRef


object SimpleProtobufProducer {

  def main(args: Array[String]): Unit = {

    val topic = "dht"

    val props = new Properties()

    props.put("acks", "all")
    props.put("retries", "5")
    props.put("enable.idempotence", "false")

    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer",    "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",  "org.apache.kafka.common.serialization.ByteArraySerializer")

    type MessageValueType = Array[Byte]

    val producer = new KafkaProducer[String, MessageValueType](props)

    for (i <- 1 to 500) {

      val key        = UUID.randomUUID().toString

      val builder    = GeoIpRecordOuterClass.GeoIpRecord.newBuilder()
      val geo        = builder
        .setBeginIp(14)
        .setEndIp(293476)
        .setCountry("USA")
        .build()

      val valueBytes = geo.toByteArray

      val record : ProducerRecord[String, MessageValueType] = new ProducerRecord[String, MessageValueType](topic, key, valueBytes)

      val metadata : RecordMetadata = producer.send(record).get()
      println(s"key = ${key}, value = ${Hex.encodeHexString(valueBytes)} "   )
    }

    producer.close()

  }


}
