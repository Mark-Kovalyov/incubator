package mayton.primes

import mayton.primes.PrimeLib._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers


class PrimesSpec extends AnyFlatSpec with Matchers {

  "Assume that primary steam" must "be correct" in {
    PrimeLib.primeCandidates.take(6) must be equals List(2,3,5,7,11,13,17)
  }

}
