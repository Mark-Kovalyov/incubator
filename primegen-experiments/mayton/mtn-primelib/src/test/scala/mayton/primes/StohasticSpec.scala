package mayton.primes

import java.util.Random

import mayton.primes.PrimeLib._
import mayton.primes.Stohastic._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class StohasticSpec extends AnyFlatSpec with Matchers {


  "Формула объединения и пересечения событий A B" must "be correct" in {
    val random = new Random()
    var a_event = false
    var b_event = false
    var intersect_a_b = 0
    var union_a_b = 0
    val iterations = 10000
    for(k <- 0 until iterations) {
      a_event = if (random.nextDouble() < 0.35) true else false
      b_event = if (random.nextDouble() < 0.27) true else false
      if (a_event && b_event) intersect_a_b = intersect_a_b + 1
      if (a_event || b_event) union_a_b = union_a_b + 1
    }
    assert(P_intersect(0.35, 0.27) === (intersect_a_b.toDouble / iterations.toDouble) +- 0.01)
    assert(P_union(0.35, 0.27) === (union_a_b.toDouble / iterations.toDouble) +- 0.01)
  }

  /**
    * Имеются три одинаковые урны. В первой урне находятся 4 белых и 7 черных шаров, во второй – только
    * белые и в третьей – только черные шары. Наудачу выбирается одна урна и из неё наугад извлекается шар.
    * Какова вероятность того, что этот шар чёрный?
    */
  "Формула полной вероятности для трех урн" must "be correct" in {
    val random = new Random()
    val iterations = 10000
    var blackBalls = 0

    for(k <- 0 until iterations) {
      val v =random.nextDouble()
      val urn = if (v < 1.0 / 3.0) 1 else if (v < 2.0 / 3.0) 2 else 3
      var blackProb = 0.0
      urn match {
        case 1 => blackProb = 7.0 / (4.0 + 7.0) // 4 белых и 7 черных шаров, вероятность достать черный (7/11)
        case 2 => blackProb = 0.0               // только белые. (0)
        case _ => blackProb = 1.0               // чёрный
      }
      if (random.nextDouble() < blackProb) blackBalls = blackBalls + 1
    }

    val res : Double = blackBalls.toDouble / iterations.toDouble

    assert(P_full(1.0 / 3.0, List(7.0 / (4.0 + 7.0), 0.0, 1.0)) === res +- 0.01)
  }

  "Формула полной вероятности для трех урн" must "be correct" in {

    
  }

}
