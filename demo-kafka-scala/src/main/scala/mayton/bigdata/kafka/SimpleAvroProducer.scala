package mayton.bigdata.kafka

import mayton.bigdata.kafka.avro.{Emp, JOBEnum}

object SimpleAvroProducer {

  def main(args: Array[String]): Unit = {
    val emp = Emp.newBuilder()
      .setENAME("enot")
      .setJOB(JOBEnum.MANAGER)
      .build()
  }

}
