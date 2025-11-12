package mayton

import mayton.markov.StringFunctions.{is_allowed_string, shiftAndBracerByTrigram}
import mayton.lib.SofarTracker
import mayton.markov.FileUtils.DEFAULT_ENCODING
import mayton.markov.{CGraphCSVExport, FileUtils, Utils, WeightGraphInt}
import org.slf4j.LoggerFactory
import org.slf4j.profiler.{Profiler, TimeInstrument}

import java.io.{FileWriter, InputStream}
import scala.io.{BufferedSource, Source}
import java.nio.charset.StandardCharsets
import scala.collection.mutable

class GraphProcessor {

  val logger = LoggerFactory.getLogger("graph-processor")

  var cgraph : WeightGraphInt[String] = null

  private def processWord(s:String, processString : String => Array[(String, String)]): Boolean = {
    if (is_allowed_string(s)) {
      val pairs : Array[(String, String)] = processString(s)
      for (pair <- pairs) {
        cgraph.addEdge(pair._1, pair._2)
      }
      true
    } else {
      false
    }
  }

  def process(params : Map[String, String]) : Either[String, Map[String, String]] = {
    val file = params("file")
    val tag = params("tag")
    val encoding = params.getOrElse("encoding", DEFAULT_ENCODING)
    
    logger.info(s"file : ${file}")
    val units = FileUtils.countCachedRows(file)
    assert(units < Integer.MAX_VALUE)
    cgraph = new WeightGraphInt[String](1)

    val sofarLines = SofarTracker.createUnitLikeTracker("line", units)

    val stopWatch = new Profiler("MarkovDemo")

    stopWatch.start("Ingest")

    val ticker = new Thread(() => {
      while(true) {
        Thread.sleep(7_000)
        sofarLines.synchronized {
          logger.info(sofarLines.toString)
        }
      }
    })
    ticker.setDaemon(true)
    ticker.start()

    val is : InputStream = FileUtils.autodetectArchiveStream(file)

    val it: BufferedSource = Source.fromInputStream(is, encoding)

    val lines = it.getLines()
    var cnt = 0
    var processed = 0
    val unique = mutable.Set[String]()
    while(lines.hasNext) {
      val line = lines.next()
      if (line.nonEmpty) {
        if (cnt % 2000 == 0) {
          sofarLines.synchronized {
            sofarLines.update(cnt)
          }
        }
        if (!unique.contains(line)) {
          unique += line
          processed += (if (processWord(line, shiftAndBracerByTrigram)) 1 else 0)
        }
      }
      cnt = cnt + 1
    }
    logger.info(s"Processed          : ${processed}")
    logger.info(s"Unique             : ${unique.size}")
    logger.info(s"Ignored by symbols : ${cnt - processed}")
    logger.info(s"All                : ${cnt}")


    is.close()

    stopWatch.start("Saving graph-frequencies")

    val stage02 = params("stage-02-folder")

    val session_id = params("session_id")
    
    val f1 = s"${stage02}/${tag}-graph-frequencies-${session_id}.csv"


    CGraphCSVExport.exportToGraphX(cgraph,
      new FileWriter(f1, StandardCharsets.UTF_8)
    )

    logger.info(s"Wrote ${f1}")

    logger.info(stopWatch.stop().toString)

    logger.info("OK")

    cgraph.clean()

    Right(params + ("graph-processor" -> cnt.toString))

  }

}
