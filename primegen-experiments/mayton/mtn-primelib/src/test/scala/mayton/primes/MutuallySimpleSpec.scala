package mayton.primes

import mayton.primes.PrimeLib._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers


class MutuallySimpleSpec extends AnyFlatSpec with Matchers {

  "10 and 21" must "be mutually simple" in {
    mutuallyPrime(10,21)
  }

  "10 and 20" must "not be mutually simple" in {
    !mutuallyPrime(10,20)
  }

}
