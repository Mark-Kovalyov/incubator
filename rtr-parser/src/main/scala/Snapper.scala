import org.slf4j.{Logger, LoggerFactory}

import java.sql.{Connection, DriverManager}

class Snapper(jdbcUrl : String, user : String, pwd : String) extends Thread {

  val logger : Logger = LoggerFactory.getLogger("Snapper")

  override def run(): Unit = {
    val connection: Connection = DriverManager.getConnection(jdbcUrl, user, pwd)
    connection.setAutoCommit(true)
    while(!isInterrupted) {
      val MAKE_SNAP =
        """
          |INSERT INTO torrent_info_consolidated
          |SELECT
          |  snap_sequence.nextval        as snap_id,
          |  count(*)                     as cnt,
          |  extract(year from published) as year
          |FROM torrent_info
          |GROUP BY ye
          |""".stripMargin
      logger.info("snap")
      try {
        Thread.sleep(15 * 1000L)
      } catch {
        case ex : InterruptedException => {
          Thread.currentThread().interrupt()
        }
      }
    }
    connection.close()
  }

}
