package mayton

import mayton.markov.FileUtils.DEFAULT_ENCODING
import mayton.markov.{FileUtils, SkewRandom, Utils}
import mayton.markov.StringFunctions.{CSV_SPLITTER, ETX, START_SEQUENCE}
import org.slf4j.LoggerFactory
import org.slf4j.profiler.Profiler

import java.io.{File, FileInputStream, PrintWriter}
import scala.collection.mutable
import scala.io.{BufferedSource, Source}

object MarkovPwGen extends Job {

  val logger = LoggerFactory.getLogger("03-pw-gen")

  def main(args:Array[String]) : Unit = {
    Utils.systemInfoWidget()
    step(Map(
      "tag"           -> args(0),
      "INPUT_FOLDER"  -> args(1),
      "OUTPUT_FOLDER" -> args(2),
      "size"          -> args(3),
      "word_limit"    -> args(4)
    ))
  }

  def step(params : Map[String, String]) : Either[String, Map[String, String]] = {
    logger.info("step wit params:")
    Utils.dumpParams(params)

    val tag           = params("tag")
    val INPUT_FOLDER  = params("stage-03-folder")
    val OUTPUT_FOLDER = params("output-folder")

    val size : Int = params("size").toInt
    val word_limit : Int = params("word_limit").toInt

    logger.info("tag = {}, size = {}, limit = {}", tag, size, word_limit)

    val stopWatch = new Profiler("markov-pw-gen")
    stopWatch.start("skew-random cache initialization")
    val files: Array[File] = (new File(INPUT_FOLDER)).listFiles()
    val prefix = s"${tag}-markov-"
    logger.info("Filtering by prefix = {}", prefix)
    val maxFile = files.map(x => x.getName).filter(x => x.startsWith(prefix)).max
    logger.info("Selected file = {}", maxFile)

    val session_id = params("session_id")

    val it: BufferedSource = Source.fromInputStream(
      new FileInputStream(INPUT_FOLDER + "/" + maxFile),
      DEFAULT_ENCODING)

    val lines = it.getLines()
    val map: mutable.Map[String, SkewRandom[Char]] = mutable.Map()
    var rows = 0
    while (lines.hasNext) {
      val line = lines.next()
      val split = line.split(CSV_SPLITTER)
      assert(split.length == 4)
      val key:  String = split(0)
      val next: String = split(1)
      val sum:  String = split(2)
      val freq: Array[Int] = split(3).split(",").map(_.toInt)
      map(key) = SkewRandom[Char](sum.toInt, freq, next.toCharArray)
      rows = rows + 1
    }
    logger.info("Rows : {}", rows)
    it.close()

    assert(map.contains(START_SEQUENCE), "Map must contains start element")
    assert(map.nonEmpty, "Map must not be empty")

    stopWatch.start("generate")

    val pw = new PrintWriter(s"${tag}-markov-pwds-${session_id}-size-${size}.csv", DEFAULT_ENCODING)
    val random = new java.util.Random()
    random.setSeed(System.currentTimeMillis())

    var limit = 0
    for(i <- 1 to size) {
      var ctx : String = START_SEQUENCE
      var isStopped = false
      limit = word_limit
      while(!isStopped && limit > 0) {
        val skewRandom: SkewRandom[Char] = map(ctx)
        limit = limit - 1
        val nextChar : Char = skewRandom.generate(random)
        if (nextChar == ETX) {
          isStopped = true
        } else {
          pw.print(nextChar)
        }
        ctx = ctx.substring(1) + nextChar
      }
      pw.println()
    }
    pw.close()
    val ti = stopWatch.stop()
    println(ti.toString)
    Right(params)
  }

}
