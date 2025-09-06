package mayton.primes

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import mayton.primes.PrimeLib._

class CombinationsSpec extends AnyFlatSpec with Matchers {

  it must "throw IllegalArgumentException any argument is negative" in {
    a [IllegalArgumentException] must be thrownBy {
      combinations(BigInt(-1),BigInt(0))
    }
    a [IllegalArgumentException] must be thrownBy {
      combinations(BigInt(0),BigInt(-1))
    }
  }

  "combinations(1,1)" must "be equals to 1" in {
    combinations(BigInt(1),BigInt(1)) must be (BigInt(1))
  }

  "combinations(1,2)" must "be equals to 2" in {
    combinations(BigInt(1),BigInt(2)) must be (BigInt(2))
  }

  "combinations(1,3)" must "be equals to 3" in {
    combinations(BigInt(1),BigInt(3)) must be (BigInt(3))
  }

  "combinations(2,3)" must "be equals to 3" in {
    combinations(BigInt(2),BigInt(3)) must be (BigInt(3))
  }

  "combinations(2,15)" must "be equals to 105" in {
    combinations(BigInt(2),BigInt(15)) must be (BigInt(105))
  }

}

