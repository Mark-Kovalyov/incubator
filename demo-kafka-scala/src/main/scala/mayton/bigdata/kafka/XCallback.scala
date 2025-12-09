package mayton.bigdata.kafka

import org.apache.kafka.clients.producer.{Callback, RecordMetadata}

class XCallback extends Callback {

  override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
    
  }
  
}
