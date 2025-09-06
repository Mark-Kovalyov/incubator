package mayton.primes

import mayton.primes.PrimeLib._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers


class PermutationSpec extends AnyFlatSpec with Matchers {

  it must "throw IllegalArgumentException any argument is negative" in {
    a [IllegalArgumentException] must be thrownBy {
      permutations(BigInt(-1),BigInt(0))
    }
    a [IllegalArgumentException] must be thrownBy {
      permutations(BigInt(0),BigInt(-1))
    }
  }

  "permutations(1,3)" must "be equals to 3" in {
    permutations(BigInt(1),BigInt(3)) must be (BigInt(3))
  }

  "permutations(2,3)" must "be equals to 6" in {
    permutations(BigInt(2),BigInt(3)) must be (BigInt(6))
  }

  "permutations(2,15)" must "be equals to 210" in {
    permutations(BigInt(2),BigInt(15)) must be (BigInt(210))
  }

  def test1: Unit = {

  }





}
