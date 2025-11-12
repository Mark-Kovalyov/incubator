package mayton

import mayton.markov.Utils
import org.slf4j.LoggerFactory

object MarkovGraphGen extends Job {

  val logger = LoggerFactory.getLogger("01-graph-gen")

  def main(args:Array[String]) : Unit = {
    logger.info("args = {}", args)

    Utils.systemInfoWidget()

    step(Map(
      "tag" -> args(0),
      "file" -> args(1),
      "encoding" -> args(2),
      "stage-01-folder" -> args(3),
      "stage-02-folder" -> args(4)
    ))
  }

  override def step(params: Map[String, String]) : Either[String, Map[String, String]] = {
    logger.info("step wit params:")
    Utils.dumpParams(params)
    val graphProcessor = new GraphProcessor()

    graphProcessor.process(params)
    
    //graphProcessor.process(params("file"), params("tag"), params("encoding"), params)
  }
}
