package mayton.primes

import mayton.primes.PrimeLib._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers


class EulerSpec extends AnyFlatSpec with Matchers {

  "Euler function" must "be correct" in {
    φ(1) must be (1)
    φ(2) must be (1)
    φ(3) must be (2)
    φ(54) must be (18)
    φ(99) must be (60)


  }

}
