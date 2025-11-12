package mayton.markov

import scala.util.Random

class SkewRandom[T](val sum : Int, val cumFreq : Array[Int], val symbols : Array[T]) {

  require(cumFreq.length == symbols.length)

  def generate(random : Random) : T = {
    val indexed_pairs : List[((Int,Int),Int)] = (0 :: cumFreq.toList).zip(cumFreq).zipWithIndex
    val r = random.nextInt(sum)
    val result : Option[((Int,Int),Int)] = indexed_pairs.find(p => r >= p._1._1 && r <= p._1._2)
    assert(result.isDefined)
    symbols(result.get._2)
  }

  override def toString: String = {
    s"$sum :: ${cumFreq} / ${symbols}"
  }

}
