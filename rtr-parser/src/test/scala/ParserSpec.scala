import org.scalacheck.Gen
/*import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuite*/

class ParserSpec {
  val evenInts = for (n <- Gen.choose(-1000, 1000)) yield 2 * n
}
