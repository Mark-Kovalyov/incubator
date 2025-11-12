package mayton.markov

import scala.collection.mutable.ListBuffer

object StringFunctions {

  // ASCII control codes

  val STX = 0x02.toChar // Start of text
  val ETX = 0x03.toChar // End of text
  //val EOT = 0x04.toChar // End of transmission
  val CSV_SPLITTER = ';'

  def special_format(s: String): String = {
    (for (c <- s) yield {
      c match {
        case STX => "[STX]"
        case ETX => "[ETX]"
        case _   => c.toString
      }
    }).mkString
  }

  val START_SEQUENCE : String = (1 to 3).map(_ => STX).mkString

  val ALLOWED_OTHER_SYMBOLS : Set[Char] = "$#@!%^&*".toCharArray.toSet

  def is_alpha_num(c:Char) : Boolean = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')

  def is_allowed_symbols(c:Char) : Boolean =  is_alpha_num(c) || ALLOWED_OTHER_SYMBOLS.contains(c)

  def is_allowed_string(s: String): Boolean = {
    !s.contains(STX) && !s.contains(ETX) && !s.contains(CSV_SPLITTER) && !s.contains(' ')
  }

  def shiftAndBracerNextSymbol(s:String, stx : Char = STX, etx : Char = ETX) : Array[(Char,Char)] = {
    val s2 = s"${stx}${s}${etx}"
    s2.toCharArray.zip(s2.toCharArray.tail)
  }

  def shiftAndBracerByNgram(s: String, n : Int, stx : Char = STX, etx : Char = ETX): Array[(String, String)] = {
    val s2 = (1 to n).map(c => stx).mkString + s + etx
    val buf: ListBuffer[(String, String)] = ListBuffer[(String, String)]()
    for (i <- 0 until s2.length - n) {
      buf += ((s2.substring(i, i + n), s2.substring(i + n, i + n + 1)))
    }
    buf.toArray
  }

  def shiftAndBracerByBigram(s: String): Array[(String, String)] = shiftAndBracerByNgram(s, 2)

  def shiftAndBracerByTrigram(s: String): Array[(String, String)] = shiftAndBracerByNgram(s, 3)

}
