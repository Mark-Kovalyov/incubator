package mayton.bigdata

import com.azure.messaging.eventhubs.models.SendOptions
import com.azure.messaging.eventhubs.{EventData, *}
import org.slf4j.LoggerFactory


object DhtEventhubPusher {

  def formatEventhubConnStr(namespace: String, eventHubName: String, sasKeyName: String, sasKey: String) : String = {
    s"Endpoint=sb://$namespace.servicebus.windows.net/;SharedAccessKeyName=$sasKeyName;SharedAccessKey=$sasKey"
  }

  val logger = LoggerFactory.getLogger("dht")

  def main(args: Array[String]) : Unit = {

    val partOptionsArr: Array[SendOptions] = (0 to 1).map(i => new SendOptions().setPartitionKey("0")).toArray

    logger.info("Starting DhtEventhubPusher")

    val EH_NAMESPACE = "enothubns"
    val EH_NAME      = "enothub"
    val KEY_NAME     = "sender"
    val EH_CONN_STR  = formatEventhubConnStr(EH_NAMESPACE, EH_NAME, KEY_NAME, "*************************")

    logger.info(EH_CONN_STR)

    // Azure Event Hub endpoints accept:
    //
    //- AMQP over port 5671
    //- AMQP over WebSockets on port 443
    //- HTTPS REST API on port 443
    //
    val producer: EventHubProducerClient  = new EventHubClientBuilder()
      .connectionString(EH_CONN_STR,EH_NAME)
      .buildProducerClient()

    val allEvents: IndexedSeq[String] = {
      for(i <- 1 to 1000) yield DhtRandom.generate()
    }

    var batch: EventDataBatch = producer.createBatch()

    for (message <- allEvents) {
      val eventData = new EventData(message)
      if (!batch.tryAdd(eventData)) {
        producer.send(batch)
        logger.info("Sended batch of size: " + batch.getCount)
        batch = producer.createBatch();
        batch.tryAdd(eventData)
      }
    }

    if (batch.getCount() > 0) {
      producer.send(batch)
      logger.info("Sended last batch of size: " + batch.getCount)
    }

    producer.close()

    logger.info("OK")

  }

}
