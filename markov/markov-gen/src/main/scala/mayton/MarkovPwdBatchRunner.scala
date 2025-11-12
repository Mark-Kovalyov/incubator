package mayton

import mayton.markov.Utils
import org.slf4j.LoggerFactory

import java.io.File

object MarkovPwdBatchRunner {

  val logger = LoggerFactory.getLogger("01-markov-pwd-batch-runner")

  def main(args:Array[String]) : Unit = {

    val application_prefix = "markov-demo"

    logger.info("ARGS : {}", args.mkString(" "))

    val sessionId = Utils.sessionIdMs()

    logger.info(s"sessionId=${sessionId}")

    val tmp = System.getProperty("java.io.tmpdir")

    val stg02 = tmp + "/" + application_prefix + "/" + sessionId + "/stage-02"
    new File(stg02).mkdirs()

    val stg03 = tmp + "/" + application_prefix + "/" + sessionId + "/stage-03"
    new File(stg03).mkdirs()


    val params = Map(
      "tag"             -> args(0),
      "file"            -> args(1),
      "encoding"        -> args(2),
      "output-folder"   -> args(3),
      "size"            -> args(4),
      "word_limit"      -> args(5),
      "stage-02-folder" -> stg02,
      "stage-03-folder" -> stg03,
      "session_id"      -> sessionId
    )

    logger.info("params = {}", params)


    for {
      j1 <- MarkovGraphGen.step(params)
      j2 <- MarkovHistGen.step(j1)
      j3 <- MarkovPwGen.step(j2)
    } yield ()


  }

}
