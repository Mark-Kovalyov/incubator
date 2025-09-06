package mayton.primes

import mayton.primes.PrimeLib._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers


class LcmSpec extends AnyFlatSpec with Matchers {

  "LCM(3,5)" must "be equals to 15" in {
    lcm("3".b,"5".b) must be ("15".b)
  }

}
