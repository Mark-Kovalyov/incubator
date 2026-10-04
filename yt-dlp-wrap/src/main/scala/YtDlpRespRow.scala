package enot

// YtDlpRespRow("248", "webm", "1920x1080",  "30"  , "vp9", None),
case class YtDlpRespRow(
       ID: String,
       ext: String,
       resolution: Option[String],
       fps: Option[String],
       vcodec: Option[String],
       acodec: Option[String]
)

object YtDlpRespRow {
  
  def fromId(id:String) = YtDlpRespRow(id, "", None, None, None, None)
  
}