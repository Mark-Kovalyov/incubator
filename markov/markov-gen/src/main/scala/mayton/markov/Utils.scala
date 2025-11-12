package mayton.markov

import org.slf4j.LoggerFactory

import java.lang.management.ManagementFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {

  val logger = LoggerFactory.getLogger("utils")

  /*
  def sessionId() : String = {
    DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm").format(LocalDateTime.now())
  }*/

  def sessionIdMs(): String = {
    DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm.SSS").format(LocalDateTime.now())
  }

  def systemInfoWidget() : Unit = {
    val memoryBean = ManagementFactory.getMemoryMXBean()
    println(f"Java version : ${System.getProperty("java.version")}")
    println(f"Max Memory   : ${memoryBean.getHeapMemoryUsage.getMax}")
  }

  def dumpParams(map : Map[String, String]) : Unit = map.foreach(item => logger.info(s"          ${item._1} = '${item._2}'"))
  
}
