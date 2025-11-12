package mayton

import mayton.markov.StringFunctions.{ETX, STX, shiftAndBracerByBigram, shiftAndBracerByTrigram, shiftAndBracerNextSymbol}
import org.scalatest.funsuite.AnyFunSuite

class StringFunctionsTest extends AnyFunSuite {

  test("Bigram") {
    val expected = Array(
       (STX, 'h'),
       ('h', 'e'),
       ('e', 'l'),
       ('l', 'l'),
       ('l', 'o'),
       ('o', ETX))
    assert(shiftAndBracerNextSymbol("hello") === expected)
  }

  test("ByBigram") {
    val expected = Array(
      ("" + STX + STX, "h"),
      ("" + STX + "h", "e"),
      ("he", "l"),
      ("el", "l"),
      ("ll", "o"),
      ("lo", "" + ETX),
    )
    assert(shiftAndBracerByBigram("hello") === expected)
  }

  test("ByTrigram") {
    val expected = Array(
      ("" + STX + STX + STX, "h"),
      ("" + STX + STX + "h", "e"),
      ("" + STX + "he", "l"),
      ("hel", "l"),
      ("ell", "o"),
      ("llo", "" + ETX)
    )
    assert(shiftAndBracerByTrigram("hello") === expected)
  }

}
