
class PGComponent extends DbComponent :
  override def upsert(torrentInfo: TorrentInfo): Boolean = true
  override def batch_upsert(torrentInfo: List[TorrentInfo]): Int = 0

