package mayton

case class HistReportEntity(
             ext:String,
             count:Int,
             extRank:Int,
             fileSize:Long) extends Ordered[HistReportEntity] :

  override def compare(that: HistReportEntity): Int =
    val i = Integer.compare(extRank, that.extRank)
    if (i == 0) (-1) * Integer.compare(count, that.count) else i



