package mayton.primes

import mayton.primes.PrimeLib._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class GcdSpec extends AnyFlatSpec with Matchers {

    "GCD(54,24)" must "be equals to 6" in {
      gcd("54".b,"24".b) must be ("6".b)
    }

    "GCD for various values" must "be correct" in {
      gcd("0".b, "0".b) must be ("0".b)
      gcd("1".b, "1".b) must be ("1".b)
      gcd("1".b, "2".b) must be ("1".b)
    }

}
