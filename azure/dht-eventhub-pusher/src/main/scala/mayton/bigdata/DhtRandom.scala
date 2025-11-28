package mayton.bigdata

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DhtRandom {

  val rand = new scala.util.Random

  def getRandomIp() : String = {
    s"${rand.nextInt(256)}.${rand.nextInt(256)}.${rand.nextInt(256)}.${rand.nextInt(256)}"
  }

  def getRandomHex(len: Int) : String = {
    val sb = new StringBuilder
    for (_ <- 0 until len) {
      val n = rand.nextInt(16)
      sb.append(f"$n%x")
    }
    sb.toString()
  }

  def generate() : String = {
    val ts = LocalDateTime.now()
    val time = ts.format(DateTimeFormatter.ISO_DATE_TIME)
    val randomIp = getRandomIp()
    val randomHex = getRandomHex(20)
    s"$time;$randomIp;0;tr;${randomHex.length / 2};$randomHex;"
  }

}
