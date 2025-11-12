package mayton

import mayton.markov.SkewRandom
import org.scalactic.Tolerance.convertNumericToPlusOrMinusWrapper
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.shouldBe

class SkewRandomTest extends AnyFunSuite {

  val ε = 0.05
  val SELECTION = 1000

  /*
  test("Single-bar skew random must always produce first symbol") {
    val skewRandomSingleBar = new SkewRandom(List(1.0f), List("A"))
    assert(skewRandomSingleBar.cumFreq === List(1.0f))
    for(i <- 1 to SELECTION) {
      assert(skewRandomSingleBar.generate() == "A")
    }
  }

  test("Two-bar random must always produce 50x50 symbol when bars are equals") {
    val skewRandomSingleBar = new SkewRandom[String](List(0.5f, 0.5f), List[String]("A", "B"))
    assert(skewRandomSingleBar.cumFreq === List(0.5f, 1.0f))
    var sum_a = 0
    var sum_b = 0
    for(i <- 1 to SELECTION) {
      val c : String = skewRandomSingleBar.generate()
      c match {
        case "A" => sum_a = sum_a + 1
        case "B" => sum_b = sum_b + 1
      }
    }
    assert(sum_a / 1000.0 === 0.5 +- ε)
    assert(sum_b / 1000.0 === 0.5 +- ε)
  }

  test("Skew-bar random must always produce 75x25 symbols ratio") {
    val skewRandomSingleBar = new SkewRandom[String](List(0.75f, 0.15f, 0.10f), List[String]("X", "Y", "Z"))
    assert(skewRandomSingleBar.cumFreq === List(0.75f, 0.9f, 1.0f))
    val sample = (1 to SELECTION).map(_ => skewRandomSingleBar.generate())
    val sum_x = sample.count(c => c == "X")
    val sum_y = sample.count(c => c == "Y")
    val sum_z = sample.count(c => c == "Z")
    assert(sum_x / 1000.0 === 0.75 +- ε)
    assert(sum_y / 1000.0 === 0.15 +- ε)
    assert(sum_z / 1000.0 === 0.10 +- ε)
  }
  
   */

}
