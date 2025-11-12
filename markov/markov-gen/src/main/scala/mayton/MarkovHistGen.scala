package mayton

import mayton.markov.FileUtils.DEFAULT_ENCODING
import mayton.markov.StringFunctions.{CSV_SPLITTER, START_SEQUENCE}
import mayton.markov.Utils
import org.slf4j.LoggerFactory
import org.slf4j.profiler.Profiler

import java.io.{File, FileInputStream, FilenameFilter, PrintWriter}
import scala.io.{BufferedSource, Source}
import scala.collection.mutable

object MarkovHistGen extends Job {

  val logger = LoggerFactory.getLogger("02-markov-hist-gen")

  def main(args:Array[String]) : Unit = {
    Utils.systemInfoWidget()

    step(Map(
      "tag" -> args(0),
      "stage-01-folder" -> args(1),
      "stage-02-folder" -> args(2)
    ))
  }

  override def step(params: Map[String, String]) : Either[String, Map[String, String]] = {
    logger.info("step with params:")
    Utils.dumpParams(params)

    val tag          = params("tag")
    val inputFolder  = params("stage-02-folder")
    val outputFolder = params("stage-03-folder")

    val stopWatch    = new Profiler("markov-hist-gen")
    stopWatch.start("generate map with histograms")
    val files: Array[File] = new File(inputFolder).listFiles()
    require(files != null, s"Files must be present in ${inputFolder}")
    val maxFile = files.map(x => x.getName).filter(x => x.startsWith(tag + "-graph-frequencies-")).max
    logger.info("Detected latest file {}", maxFile)

    val it: BufferedSource = Source.fromInputStream(
        new FileInputStream(inputFolder + "/" + maxFile),
        DEFAULT_ENCODING)

    val lines = it.getLines()
    val map : mutable.Map[String, mutable.ListBuffer[(Char,Int)]] = mutable.Map()
    var rows = 0
    while(lines.hasNext) {
      val line = lines.next()
      val split = line.split(CSV_SPLITTER)
      assert(split.size == 3, "Histogram must be 3 columns!")
      val key:  String = split(0)
      val next: String = split(1)
      val freq: String = split(2)
      if (map.contains(key)) {
        val listBuf = map(key)
        listBuf += ((next(0), freq.toInt))
      } else {
        val listBuf = mutable.ListBuffer[(Char,Int)]()
        listBuf += ((next(0), freq.toInt))
        map(key) = listBuf
      }
      rows = rows + 1
    }
    logger.info("Rows : {}", rows)
    it.close()

    assert(map.contains(START_SEQUENCE), s"MarkovPassGen :: Must must contain initial state `$START_SEQUENCE`")

    val session_id = params("session_id")
    val pw = new PrintWriter(s"${outputFolder}/${tag}-markov-${session_id}.csv")
    var maxKey = ""
    var maxSum = 0
    for(i <- map) {
      val listbuf = i._2
      assert(listbuf.nonEmpty, s"List buffer must not be empty (key = `${i._1}`)!")
      assert(listbuf.forall(x => x._2 > 0), "each list buffer frequency value must be > 0")
      val key = i._1
      val sum = listbuf.map(_._2).sum
      if (sum > maxSum && maxKey != START_SEQUENCE) {
        maxSum = sum
        maxKey = key
      }
      val sortedPairs: mutable.ListBuffer[(Char, Int)] = listbuf.sortBy((p1, p2) => -p2)
      val edgeLabels = sortedPairs.map(_._1).mkString
      var xsum = 0
      val edgeFreqs = (for(pair <- sortedPairs) yield { xsum = xsum + pair._2 ; xsum }).mkString(",")
      pw.println(s"$key;$edgeLabels;$sum;$edgeFreqs");
    }

    pw.close()

    logger.info("Max key : {}", maxKey)

    logger.info(stopWatch.stop().toString)
    Right(params + ("histogram-rows" -> rows.toString))
  }


}
