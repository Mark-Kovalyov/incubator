import scala.util.matching.Regex
import scala.jdk.CollectionConverters.*
import scala.jdk.StreamConverters.*
import scala.language.postfixOps
import scala.math.Numeric.IntIsIntegral.sign
import scala.math.abs

import java.sql.{DriverManager, PreparedStatement}
import java.time.{DateTimeException, LocalDate}
import java.util.Locale

import org.jsoup.*
import org.jsoup.nodes.*
import org.jsoup.select.*
import org.slf4j.{Logger, LoggerFactory}
import DHTTool.{extractOnlyBtihCode, fromBtihToMagnet}
import RutorUtils.{extractDate, extractId}
import mayton.lib.SofarTracker

object RtrParse {

  val logger : Logger = LoggerFactory.getLogger("RtrParse")


  var batchCount = 0
  val BATCH_SIZE = 128

  val rtrKafkaClient : RtrKafkaClient = new RtrKafkaClient()

  def pushKafka(tinf: TorrentInfo) : Unit = {
    rtrKafkaClient.send(tinf)
  }

/*  def saveBatch(tinf: TorrentInfo, pst: PreparedStatement): Unit =
    pst.setInt(1, tinf.id)
    pst.setString(2, tinf.magnet)
    pst.setString(3, tinf.name)
    pst.setDate(4, DbTool.fromLocalDateToSQLDate(tinf.published))
    pst.setInt(5, tinf.page)
    pst.addBatch()
    batchCount = batchCount + 1
    if (batchCount == BATCH_SIZE)
      batchCount = 0
      pst.clearParameters()
      val scalaResults : LazyList[Int] = pst.executeBatch().to(LazyList)
      pst.getConnection.commit()
      println("commit")
      val skipped2 : Int = scalaResults.count(res => res == 0)
      val updated2 : Int = scalaResults.count(res => res != 0)
      println(s"Batch result : skipped2 = ${skipped2}, updated2 = ${updated2}")*/

/*  def finalizeBatch(pst: PreparedStatement): Unit =
    if (batchCount > 0)
      pst.clearParameters()
      val scalaResults : LazyList[Int] = pst.executeBatch().to(LazyList)
      val skipped2 : Int = scalaResults.count(res => res == 0)
      val updated2 : Int = scalaResults.count(res => res != 0)
      println(s"Final batch result : skipped2 = ${skipped2}, updated2 = ${updated2}")

  def save(tinf: TorrentInfo, pst: PreparedStatement): Int =
    pst.setInt(1, tinf.id)
    pst.setString(2, tinf.magnet)
    pst.setString(3, tinf.name)
    pst.setDate(4, DbTool.fromLocalDateToSQLDate(tinf.published))
    pst.setInt(5, tinf.page)
    pst.executeUpdate()*/

  def processRow(element : Element, page:Int) : TorrentInfo =
    val dtDate : String = element.child(0).text()
    val ahref : Elements = element.select("a[href]")
    val ahrefScalaSeq : Seq[Element] = ahref.stream().toScala(LazyList)
    val downgifClassTag : Option[String] = ahrefScalaSeq.flatMap {
      x => {
        val href : String = x.attributes().get("class")
        if (href == "downgif") {
          Some(x.attributes().get("href"))
        } else {
          None
        }
      }}.headOption

    val id : Option[String] = if (downgifClassTag.isDefined) extractId(downgifClassTag.get) else None

    val magnetOpt : Option[String] = ahrefScalaSeq.flatMap {
      x => {
        val href : String = x.attributes().get("href")
        if (href.startsWith("magnet")) Some(href) else None
      }}.headOption

    val nameOpt : Option[String] = ahrefScalaSeq.flatMap {
      x => {
        val href : String = x.attributes().get("href")
        if (href.startsWith("/torrent")) Some(x.text) else None
      }}.headOption

    val dt : Option[LocalDate] = extractDate(dtDate)

    val tinfo = TorrentInfo(
      id.get.toInt,
      extractOnlyBtihCode(magnetOpt.get),
      nameOpt.get,
      dt.get,
      page)
    tinfo


  def processPage(host: String, page : Int, tracker : SofarTracker, magnets : Int) : Int =
    println(tracker.toString)
    println(s"page = ${page}")
    var newMagnets = magnets
    val path = s"${host}/browse/${page}/0/0/0"
    println(path)
    val connect: Connection = Jsoup.connect(path)
    val doc: Document = connect.get()
    val html : Element = doc.select("html").first

    val tbody: Element = html
      .selectFirst("body")
      .selectFirst("div[id=ws]")

    val gaiit : java.util.Iterator[Element] = tbody.select("tr[class=gai]").iterator()

    while(gaiit.hasNext)
      val torrentInfo : TorrentInfo = processRow(gaiit.next(), page)
      //saveBatch(torrentInfo, pst)
      pushKafka(torrentInfo)
      newMagnets = newMagnets + 1

    val tumit : java.util.Iterator[Element] = tbody.select("tr[class=tum]").iterator()

    while(tumit.hasNext)
      val torrentInfo : TorrentInfo = processRow(tumit.next(), page)
      //saveBatch(torrentInfo, pst)
      pushKafka(torrentInfo)
      newMagnets = newMagnets + 1
    newMagnets

  def processPages(connection: java.sql.Connection, host: String, fromPage: Int, toPage : Int): (Int, Int, Int) =
    //val pst : PreparedStatement = connection.prepareStatement(INSERT_SQL_TEXT)
    var page_cnt = 0
    var magnets = 0
    val updatedRows = 0
    val step = sign(toPage - fromPage)
    val normBegin = scala.math.min(fromPage, toPage)
    val normEnd = scala.math.max(fromPage, toPage)
    val size = normEnd - normBegin
    val tracker = SofarTracker.createUnitLikeTracker("page", size)
    // [20..45] size = 25
    for (page <- fromPage until toPage by step)
      if (step < 0)
        tracker.update(normEnd - page)
      else
        tracker.update(page - normBegin)
      page_cnt = page_cnt + 1
      // pst : PreparedStatement, host: String, page : Int, tracker : SofarTracker, magnets : Int
      processPage(host, page, tracker, magnets)

    //finalizeBatch(pst)
    tracker.finish()
    println(tracker.toString)
    (page_cnt, magnets, updatedRows)

  def main(args : Array[String]) : Unit =
    val fromPage = args(0).toInt
    val toPage   = args(1).toInt

    val user = System.getenv("DB_USER")
    val pwd  = System.getenv("DB_PWD")
    val connection: java.sql.Connection = DriverManager.getConnection(Constants.JDBC_URL, user, pwd)
    val snapper = new Snapper(Constants.JDBC_URL, user, pwd)
    snapper.start()
    connection.setAutoCommit(false)
    // 0..5158
    // http://rutor.info/browse/5160/0/0/0
    //
    val (pages, urls, updatedRows) = processPages(connection, "http://rutor.info", fromPage, toPage)
    connection.commit()
    println(s"Successfully parsed ${pages} pages and ${urls} urls, updated rows : ${updatedRows}")
    connection.close()
    snapper.interrupt()

}

