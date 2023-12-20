package mayton.crypto

import org.scalacheck.{Arbitrary, Gen, Properties}
import org.scalacheck.Prop.forAll
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen.oneOf

import java.awt.Color
import scala.::
import scala.annotation.tailrec

object RleProperties extends Properties("RLE") {

  /*property("feedback-test-alpha-strings") = forAll(narrowABCGen) {
    (a: String) => Rle.decode1(Rle.encode1(a)) == a
  }*/

  //def randomNumeric(): String = org.apache.commons.lang3.RandomStringUtils.randomNumeric(100)

  // Arbitrary
  //val arbBool: Arbitrary[Boolean] = Arbitrary(oneOf(true, false))
  // val arbInt : Arbitrary[Int] = ???

  def randomAlphabetic(n: Int): String = org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(n)

  def narrowABC(n: Int): String = org.apache.commons.lang3.RandomStringUtils.random(n, 'A','B','C')

  def infseq_lazylist(n : Int) : LazyList[String] = LazyList.continually(randomAlphabetic(n))

  def narrowABCSeq(n: Int): Seq[String] = for(i <- 1L to Int.MaxValue) yield narrowABC(n)


  // Gens

  //val narrowABCGen : Gen[String] = Gen.oneOf(infseq_lazylist)

  //val evenInts : Gen[Int] = for (n <- Gen.choose(-1000, 1000)) yield 2 * n
  //val colorGen : Gen[Color] = Gen.oneOf(Color.BLACK, Color.RED)
  //val coordGen : Gen[(Int,Int)] = for { i <- arbitrary[Int]; j <- arbitrary[Int] } yield (i, j)

}
