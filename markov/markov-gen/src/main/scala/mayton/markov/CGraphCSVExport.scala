package mayton.markov

import mayton.markov.StringFunctions.CSV_SPLITTER

import java.io.{PrintWriter, Writer}

object CGraphCSVExport {

  def exportToGraphX(graph : WeightGraphInt[String], w1: Writer): Unit = {
    val pw1 = new PrintWriter(w1)
    for ((k, v) <- graph.emap) {
      pw1.print(k._1)
      pw1.print(CSV_SPLITTER)
      pw1.print(k._2)
      pw1.print(CSV_SPLITTER)
      pw1.println(v.toString)
    }
    pw1.close()
  }

}
