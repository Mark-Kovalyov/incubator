package enot

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.verbs.ShouldVerb

import java.io.FileReader
import scala.collection.JavaConverters.asScalaBufferConverter

class Test1 extends AnyFlatSpec {

  def test1() : Unit = {
    val input =
      """248   webm  1920x1080   30    │  202.73MiB  826k https │ vp9            826k video only          1080p, webm_dash
        |399   mp4   1920x1080   30    │  164.20MiB  669k https │ av01.0.08M.08  669k video only          1080p, mp4_dash
        |{ "id": "sAE7Mihn_B0" }""".stripMargin

    val output = YtDlpResponceParser.extractJson(input)
    assert("{ \"id\": \"sAE7Mihn_B0\" }" == output)
  }

  def testFilter() : Unit = {
    val input = List(
      YtDlpRespRow("248", "webm", Some("1920x1080"), Some("30"), Some("vp9"), None),
      YtDlpRespRow("399", "mp4",  Some("1920x1080"), Some("30"), Some("av01.0.08M.08"), None)
    )
  }

  def test2() : Unit = {
    val input = org.apache.commons.io.IOUtils.readLines(new FileReader("src/test/resources/stdout.json")).asScala.mkString
    assert(input != null)
  }

}
