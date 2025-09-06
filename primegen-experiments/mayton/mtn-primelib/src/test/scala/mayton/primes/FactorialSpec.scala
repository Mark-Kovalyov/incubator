package mayton.primes

import mayton.primes.PrimeLib._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class FactorialSpec extends AnyFlatSpec with Matchers {

  "Assume that factorial of 0" must "equals 1" in {
    val x = fact(BigInt(0))
    x must be (BigInt(1))
  }

  "Assume that factorial of 1" must "equals 1" in {
    val x = fact(BigInt(1))
    x must be (BigInt(1))
  }

  "Assume that factorial of 5" must "equals 120" in {
    val x = PrimeLib.fact(BigInt(5))
    x must be (BigInt(120))
  }

  it must "throw IllegalArgumentException if argument is negative" in {
    a [IllegalArgumentException] must be thrownBy {
      fact(BigInt(-1))
    }
  }




}
