package mayton.markov

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream
import org.slf4j.LoggerFactory

import java.io.{BufferedInputStream, DataInputStream, DataOutputStream, File, FileInputStream, FileOutputStream, InputStream}
import java.util.zip.GZIPInputStream
import scala.util.Using

object FileUtils {

  val DEFAULT_ENCODING = "UTF-8"

  val logger = LoggerFactory.getLogger("file-utils")

  def wrapBufferedInStr(filename:String) : BufferedInputStream = {
    new BufferedInputStream(new FileInputStream(filename), 16 * 1024)
  }

  def autodetectArchiveStream(generic : String) : InputStream = {
    val extension = generic.substring(generic.lastIndexOf(".")).toLowerCase
    extension match {
      case ".bz2" | ".bzip2" => new BZip2CompressorInputStream(wrapBufferedInStr(generic))
      case ".gz" | ".gzip"   => new GZIPInputStream(wrapBufferedInStr(generic))
      case ".xz"             => new XZCompressorInputStream(wrapBufferedInStr(generic))
      case _                 => wrapBufferedInStr(generic)
    }
  }

  def countCachedRows(file: String): Long = {
    logger.info("autodetecting rowcount...")
    val ccrFile = file + ".cachedrows"
    var res = 0L
    if (new File(ccrFile).exists()) {
      Using.Manager { use =>
        val reader = use(new DataInputStream(new BufferedInputStream(new FileInputStream(ccrFile))))
        res = reader.readLong()
        logger.info(s"detected ${res} rows from cache file ${ccrFile}")
        assert(res >= 0)
      }
      res
    } else {
      res = countRows(file)
      Using.Manager { use =>
        val dos = new DataOutputStream(new FileOutputStream(ccrFile))
        dos.writeLong(res)
      }
      res
    }
  }

  def countRows(file: String): Long = {
    val inputStream : InputStream = autodetectArchiveStream(file)
    val buf = Array.ofDim[Byte](8192)
    var res = 0
    var rowCnt = 0L
    while ( { res = inputStream.read(buf); res > 0 }) {
      for (i <- 0 until res if buf(i) == 0x0a) rowCnt = rowCnt + 1
    }
    inputStream.close()
    logger.info(s"detected ${rowCnt} rows from physical file ${file}")
    rowCnt
  }

}
