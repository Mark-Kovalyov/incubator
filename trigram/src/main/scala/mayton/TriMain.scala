package mayton

import com.google.common.math.Quantiles.percentiles

import java.io.{FileWriter, PrintWriter}
import java.util
import java.util.StringJoiner
import scala.collection.{IndexedSeq, mutable}
import scala.io.Source.fromFile
import scala.jdk.CollectionConverters.*
import scala.jdk.StreamConverters.*
import scala.math._

object TriMain {

  val LN2 = log(2.0)

  def get_optimal_k(m: Long, n: Long): Int = (round(m.toDouble / n.toDouble) * LN2).toInt

  def get_fpp_by_m_n(m: Long, n: Long): Double =
    val k = get_optimal_k(m, n)
    val fn = exp(-k * n.toDouble / m.toDouble)
    pow(1.0 - fn, k.toDouble)

  def calculate_percentile(data : List[Double], percentile : Double) : Double =
    import scala.math.abs
    val sorted_data = data.sorted
    val index : Double = (percentile / 100.0) * ( sorted_data.length - 1) + 1.0
    if (abs(index - index.toInt.toDouble) < 0.001)
      sorted_data(index.toInt - 1)
    else
      val lower_index: Int = index.toInt - 1
      val upper_index: Int = index.toInt
      val weight: Double = index
      (1 - weight) * sorted_data(lower_index) + weight * sorted_data(upper_index)

  def is_lat(ch : Char) : Boolean = ch.isLetter

  def trigrams(sentence: String): List[String] =
    sentence
      .toCharArray                           // The quick brown fox Leda #295 jumps <ip=127.0.0.1>
      .map(c => c.toLower)                   // the quick brown fox leda #295 jumps <ip=127.0.0.1>
      .map(c => if (is_lat(c) || c.isDigit || c == ' ' || c == '.' || c == ':' || c == '-') c else ' ')  // the quick brown fox leda  295 jumps  ip 127.0.0.1
      .mkString
      .split(" ")  // the, quick, brown, fox, leda,  295, jumps,  ip 127.0.0.1
      .filter(x => x.length >= 3) // the, quick, brown, fox, leda,  295, jumps, 127.0.0.1
      .flatMap(word => trigramsWord(word).toList) //
      .toSet
      .toList


  def trigramsWord(word: String): IndexedSeq[String] = {
    val chars: List[Char] = word.toLowerCase.toCharArray.toList
    for (i <- 0 until chars.length - 2) yield {
      "" + chars(i) + chars(i + 1) + chars(i + 2)
    }
  }

  def main(args:Array[String]) : Unit = {

    val lines : Iterator[String] = fromFile("/bigdata/sftp-sync/logs/ftp-sync.log").getLines
    var sum = 0
    var rows = 0

    val trigramWriter = new PrintWriter(new FileWriter("trigrams.csv"))

    var sums = mutable.Buffer[Double]()
    var stringLengthCum : Int = 0
    for(line <- lines) {
      stringLengthCum = stringLengthCum + line.length
      print(line + " ::: ")
      val trg = trigrams(line)
      val sj = new StringJoiner(" ","","")
      trg.foreach(trigram => print(s"[${trigram}] "))
      trg.foreach(trigram => sj.add(trigram))
      println()
      sum = sum + trg.length
      rows = rows + 1
      sums.append(trg.length)

      trigramWriter.println(sj.toString)
    }
    println(s"avg string length    : ${stringLengthCum/rows}")
    println(s"avg trigrams per row : ${sum/rows}")
    trigramWriter.close()

    val doubleJulList    : java.util.ArrayList[Double] = new util.ArrayList[Double]()

    val coll : java.util.Collection[Double] = new util.ArrayList[Double]()

    val doublePrimitives : Array[Double] = Array.fill[Double](100)(1.0)

    val persentile95 : Double = calculate_percentile(sums.toList, 95.0)
    val persentile75 : Double = calculate_percentile(sums.toList, 75.0)

    println(s"95th percentile : ${persentile95}" )
    println(s"75th percentile : ${persentile75}" )

    var bitcard : Long = 512 //bit

    while(bitcard > 32) {
      val k = get_optimal_k(bitcard, persentile95.toLong)
      val fpp = get_fpp_by_m_n(bitcard, persentile95.toLong)
      printf(s"Bloom parameters : k=${k}, fpp=${String.format("%.4f",fpp)}, bitcard=${bitcard} bits (${bitcard / 8} bytes)\n")
      bitcard = bitcard / 2
    }
  }

}
