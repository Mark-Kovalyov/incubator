package mayton

import scala.math._
import scala.annotation.tailrec

object BloomFilterMain extends App:


  @tailrec
  def hrfs(i: BigInt, m: List[String] = List("BYTES", "K", "M", "G", "T", "P", "Z")): String = {
    if (i < 1024 || m.tail == Nil) "" + i + " " + m.head else hrfs(i / 1024, m.tail)
  }


  enum BloomResult:
    case PROBABLE_YES, EXACTLY_NO

  abstract class BloomFilter[K]:
    def put(key: K): Unit

    def exists(key: K): BloomResult

    def count: Int

  class FlexibleBloomFilter extends BloomFilter[String]:
    var cnt = 0

    override def put(key: String): Unit = {}

    override def exists(key: String): BloomResult = BloomResult.PROBABLE_YES

    override def count = cnt


  val vp = List("095", "050", "099", "066")

  println(s"Vodadone prefixes : ${vp}")

  val range = 0 to 999_99_99
  println(s"Numerical capacity for 7th digits number : ${range.size}")

  val n = vp.length * range.size // 400 000 00

  println(s"n = ${n}")

  val LN2 = log(2.0)

  def get_optimal_k(m: Long, n: Long): Int = (round(m.toDouble / n.toDouble) * LN2).toInt

  def get_fpp_by_m_n(m: Long, n: Long): Double =
    val k = get_optimal_k(m, n)
    val fn = exp(-k * n.toDouble / m.toDouble)
    pow(1.0 - fn, k.toDouble)

  val fpp_vodafone = get_fpp_by_m_n(_, range.size)

  var m = 10 * 1024 * 1024

  println("------------------------------------------------")
  println("| m(bits)   | m(physical size) |   fpp    |  k |")
  println("------------------------------------------------")

  // while ({ <body> ; <cond> }) ()

  // while
  //  val x: Int = iterator.next
  //  x >= 0
  // do print(".")

  while (fpp_vodafone(m) > 0.00001)
    val fpp = fpp_vodafone(m)
    val k = get_optimal_k(m, n)
    printf("|%10d |      %10s  | %1.6f | %2d |\n", m, hrfs(m / 8), fpp, k)
    m = m + m / 5


  println("-----------------------------------------------")

  val r = new java.util.Random()

  def randomVodafone(): String = "+38(" + vp(r.nextInt(vp.length)) +
    ")%03d".format(r.nextInt(1000)) + "-" +
    "%02d".format(r.nextInt(100)) + "-" +
    "%02d".format(r.nextInt(100))

  val numLen = randomVodafone().length

  (1 to 20).foreach(x => println(randomVodafone()))

  println(s"Storage space required for ${n} vodafone numbers : ${hrfs(n * numLen)}")



