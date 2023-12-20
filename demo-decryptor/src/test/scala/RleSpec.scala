package mayton.crypto

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.flatspec.AnyFlatSpec

class RleSpec extends AnyFunSuite {

  test("RLE encoder") {
    assert(Rle.encode1("AAAAAAAABCCCCXXXXX") == "A8B1C4X5")
    assert(Rle.encode1("") == "")
    assert(Rle.encode1("X") == "X1")
  }

  test("RLE decoder") {
    assert(Rle.decode1("A8B1C4X5") == "AAAAAAAABCCCCXXXXX")
    assert(Rle.decode1("") == "")
    assert(Rle.decode1("X2") == "XX")
    assert(Rle.decode1("Y10") == "YYYYYYYYYY")
  }

}
