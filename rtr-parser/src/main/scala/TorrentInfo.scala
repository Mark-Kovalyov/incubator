import java.time.LocalDate

case class TorrentInfo(id: Int, magnet: String, name: String, published: LocalDate, page: Int) :
  override def toString: String = s"id = '${id}', magnet = '${magnet}', name = '${name}', pub = '${published}', page = ${page}"

