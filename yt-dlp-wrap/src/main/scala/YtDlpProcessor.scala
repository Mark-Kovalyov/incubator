package enot

import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters.*

object YtDlpProcessor {
  
  val logger = LoggerFactory.getLogger("yt-dlp-processor") 

  def callYtDlp(args: List[String]): String = {
    val javaArray: java.util.List[String] = ("yt-dlp" :: args).asJava
    val builder = new ProcessBuilder(javaArray)
    val process : Process = builder.start()
    val stdout : Array[Byte] = process.getInputStream.readAllBytes()
    val stderr : Array[Byte] = process.getErrorStream.readAllBytes()
    val exitCode : Int = process.waitFor()

    if (exitCode != 0) {
      val error = String(stderr, java.nio.charset.StandardCharsets.UTF_8)
      logger.error(s"yt-dlp failed with exit code {} : {}", exitCode, error)
    }

    String(stdout, java.nio.charset.StandardCharsets.UTF_8)
  }



}
