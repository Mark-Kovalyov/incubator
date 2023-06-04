abstract class DbComponent :

  def upsert(torrentInfo: TorrentInfo) : Boolean

  def batch_upsert(torrentInfo: List[TorrentInfo]) : Int
