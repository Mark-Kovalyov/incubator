import java.net.URLEncoder

object DHTTool :

  def fromBtihToMagnet(btih : String, name : String) : String = s"magnet:?xt=urn:btih:${btih}&dn=${URLEncoder.encode(name)}"

  // TODO: Improove
  def extractOnlyBtihCode(magnet : String) : String = magnet.substring(20, 40 + 20)


