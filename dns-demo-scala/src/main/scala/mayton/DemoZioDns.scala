package mayton

import org.apache.commons.csv.{CSVFormat, CSVParser, CSVRecord}
import zio.stream.ZStream
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}
import scala.jdk.OptionConverters.*
import scala.jdk.CollectionConverters.*
import scala.jdk.StreamConverters.*


import java.io.FileReader

case class ReportError(comment : String)

object DemoZioDns extends ZIOAppDefault {


  val root = ""

  case class IP(ip: String, ptr: String)

  def sequenceOfIpRecords(): Seq[IP] = {
    val parser = CSVParser.parse(
      new FileReader(root + "/udp-groupped.csv"),
      CSVFormat.newFormat(';').withFirstRecordAsHeader)

    val it: Iterator[CSVRecord] = parser.getRecords().iterator().asScala
    Seq(IP("", ""))
  }

  val aStream: ZStream[Any, Nothing, IP] = ZStream.fromIterable(sequenceOfIpRecords())


  def domainByIp(ip: String): ZIO[Any, String, String] = {
    //  ZIO.fail("Unable to resolve ")
    ZIO.succeed(ip)
  }

  def userByEmail(str: String): ZIO[String, String, String] = {
    ZIO.succeed("user")
  }

  def userCosts(str: String): ZIO[String, String, String] = {
    ZIO.succeed("user")
  }

  def getReport(str: String): ZIO[String, String, String] = {
    ZIO.succeed("user")
  }






}
