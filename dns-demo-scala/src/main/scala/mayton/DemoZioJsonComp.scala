package mayton

import com.google.gson.JsonPrimitive
import org.apache.commons.csv.{CSVFormat, CSVPrinter}
import org.apache.commons.lang3.tuple.Pair
import org.jsfr.json.*
import org.jsfr.json.compiler.JsonPathCompiler
import org.jsfr.json.provider.GsonProvider
import zio.{Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.BufferedSource
//import scala.collection.JavaConversions._

object DemoZioJsonComp {

  def antiSplit(s: String, predicate: Char => Boolean) : List[String] = {
    val lst = new ListBuffer[String]
    var reuse = new StringBuilder
    var state = 0
    for(c <- s.toCharArray.toList) {
      val p = predicate(c)
      (p, state) match {
        case (true, 0) => {
          reuse.append(c)
          state = 1
        }
        case (false, 0) => state = 0
        case (true, 1) => reuse.append(c)
        case (false, 1) => {
          lst.append(reuse.toString)
          reuse = new StringBuilder
          state = 0
        }
      }
    }
    lst.toList
  }

    def saveToCsv(file: String, sortedPairs: Seq[(String, Int)]): Int = {
      val printer : CSVPrinter = new CSVPrinter(new FileWriter(file), CSVFormat.EXCEL)
      var cnt : Int = 0
      sortedPairs.foreach((k,v) => {
        printer.printRecord(k,v)
        cnt = cnt + 1
      })
      printer.close()
      cnt
    }

    case class Product(desc: String)


    def jsonHistogram(inputJsonLines: String, jsonPathExpression: String) : Map[String, Int] = {
      val br = new BufferedReader(new FileReader(inputJsonLines))
      val dictHist2 = new mutable.HashMap[String, Int]()
      var buf = ""
      val surfer = new JsonSurfer(GsonParser.INSTANCE, GsonProvider.INSTANCE)
      while ( { buf = br.readLine() ; buf != null } ) {
        val iterator = surfer.iterator(buf, JsonPathCompiler.compile(jsonPathExpression))
        if (iterator.hasNext) {
          val obj = iterator.next.asInstanceOf[JsonPrimitive]
          val value = obj.getAsString
          val words : List[String] = antiSplit(value, Character.isLetter)
          for (word <- words) {
            if (dictHist2.contains(word)) dictHist2(word) = dictHist2(word) + 1
            else dictHist2.put(word, 1)
          }
        }
      }
      br.close()
      dictHist2.toMap
    }

    def main(args: Array[String]): Unit = {
      val dictHist = jsonHistogram("?.jsonl", "$['description']")

      val sortedPairs : Seq[(String, Int)] = dictHist
        .toList
        .map((k,v) =>Tuple2(k,v))
        .filter(x => x._2 > 1)
        .filter(x => x._1.length > 6)

      saveToCsv("out.csv", sortedPairs)

    }


}
