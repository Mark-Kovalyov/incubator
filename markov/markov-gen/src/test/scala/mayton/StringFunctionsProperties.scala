package mayton

import mayton.markov.StringFunctions.{STX, shiftAndBracerByTrigram}
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}


object StringFunctionsProperties extends Properties("StringFunctions") {

  val alphaNumCharGen: Gen[Char] = Gen.alphaNumChar

  val infiniteStringStream: Gen[Stream[Char]] = Gen.infiniteStream(alphaNumCharGen)

  def stringStreamOfSize(size: Int): Gen[String] = infiniteStringStream.map(_.take(size).mkString)

  property("encoded string must produce array with specific length") = forAll(stringStreamOfSize(7)) { (str: String) =>
    shiftAndBracerByTrigram(str).length == (str.length + 1)
  }

}
