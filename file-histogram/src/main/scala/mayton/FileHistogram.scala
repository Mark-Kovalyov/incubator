package mayton

import java.io.{File, FileWriter, PrintWriter}
import java.nio.charset.StandardCharsets
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, FileVisitor, Files, Path, Paths, SimpleFileVisitor}
import java.util.regex.Matcher
import scala.annotation.tailrec
import scala.util.matching.Regex
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import scala.collection.mutable
import scala.jdk.CollectionConverters.*
import scala.jdk.StreamConverters.*
import scala.util.Using

object FileHistogram {

  def calc(path: String, extensions: String): Map[String, HistReportEntity] = {
    val map = mutable.Map[String, HistReportEntity]()
    Files.walkFileTree(Path.of(path), new SimpleFileVisitor[Path] {

      override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult = {
        if (dir.getFileName.toString.startsWith("."))
          FileVisitResult.SKIP_SUBTREE
        else
          FileVisitResult.CONTINUE
      }

      override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
        val spattern = "^(?<filename>.+\\.)(?<extension>" + extensions + ")$"
        val pattern: Regex = spattern.r
        val fileName = file.getFileName.toString
        val matcher: Matcher = pattern.pattern.matcher(fileName)
        val ext: String = if (matcher.matches()) matcher.group("extension") else "*"
        val entity: HistReportEntity = if (map.contains(ext)) {
          val old = map(ext)
          HistReportEntity(ext, old.count + 1, old.extRank, old.fileSize + file.toFile.length())
        } else {
          HistReportEntity(ext, 1, if ext == "*" then 1 else 0, file.toFile.length())
        }
        map(ext) = entity
        FileVisitResult.CONTINUE
      }
    })
    extensionsToList(extensions)
      .foreach(ext => {
        if (!map.contains(ext)) {
          map += (ext -> HistReportEntity(ext, 1, 0, 0L))
        }
      })
    map.toMap
  }

  def extensionsToList(extensionsSeparated: String): List[String] = extensionsSeparated.split('|').toList

  def main(args: Array[String]): Unit = {
    val tag = args(0)
    val home = args(1)
    val extensions = args(2)
    val date = LocalDateTime.now()
    val sdate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm"))
    val path = "reports/" + tag + "/" + sdate
    new File(path).mkdirs()
    println(s"File report for ${home} with extensions : ${extensions}")
    val map = calc(home, extensions)
    val hr = HistReport(date, tag, "", map.values.toList)

    Using(new PrintWriter(path + "/" + tag + ".md", StandardCharsets.UTF_8)) {
      writer => MarkupWriter.write(writer, hr)
    }

    Using(new PrintWriter(path + "/" + tag + ".txt", StandardCharsets.UTF_8)) {
      writer => ConsoleWriter.write(writer, hr)
    }

    {
      val writer = new PrintWriter(System.out)
      ColoredConsoleWriter.write(writer, hr)
      writer.flush()
    }

    {
      val writer = new PrintWriter(System.out)
      ConsoleWriter.write(writer, hr)
      writer.flush()
    }
  }


}