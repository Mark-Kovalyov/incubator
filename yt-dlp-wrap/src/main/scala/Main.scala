package enot

object Main {


  def collectFormats(url: String): List[YtDlpRespRow] = {
    val formatList = YtDlpProcessor.callYtDlp("-F" :: url :: Nil)
    val parsed: List[YtDlpRespRow] = YtDlpResponceParser.parse(formatList)
    parsed
  }

  def selectBestVideo(input : List[YtDlpRespRow]): Option[YtDlpRespRow] = {
    // best video (mp4, avc1.4D4020, res = 1280x720)
    // 311   mp4   1280x720    50    │ ~266.01MiB  3782k m3u8  │ avc1.4D4020    3782k video only
    // 298   mp4   1280x720    50    │  100.54MiB  1429k https │ avc1.4d4020    1429k video only          720p50, mp4_dash
    // 612   mp4   1280x720    50    │ ~256.68MiB  3650k m3u8  │ vp09.00.40.08  3650k video only
    // 302   webm  1280x720    50    │   94.13MiB  1338k https │ vp9            1338k video only          720p50, webm_dash
    // 398   mp4   1280x720    50    │   44.89MiB   638k https │ av01.0.08M.08   638k video only          720p50, mp4_dash
    // 312   mp4   1920x1080   50    │ ~436.34MiB  6204k m3u8  │ avc1.64002A    6204k video only
    val bestVideo = input.filter { 
      row =>
        row.ext == "mp4" &&
        row.resolution.contains("1280x720") &&
        row.vcodec.exists(_.startsWith("avc1."))
    }
    bestVideo.headOption
  }

  def selectBestAudio(input : List[YtDlpRespRow]): Option[YtDlpRespRow] = {
    // best audio (m4a, ru, high quality)
    val bestVideo = input.filter { 
      row => row.ext == "m4a" &&
             row.vcodec.exists(_.startsWith("avc1."))
    }
    bestVideo.headOption
  }

  def download(url: String, videoFormat : YtDlpRespRow, audioFormat : YtDlpRespRow): Unit = {
    val respDownload = YtDlpProcessor.callYtDlp(
      "-f" :: videoFormat.ID + "+" + audioFormat.ID :: Nil)
  }

  def main(args: Array[String]): Unit = {
    val url = "https://www.youtube.com/watch?v=sAE7Mihn_B0"

    val collected = collectFormats(url)

    val bestVideoFormat = selectBestVideo(collected)
    val bestAudioFormat = selectBestVideo(collected)

    if (bestAudioFormat.isDefined && bestAudioFormat.isDefined){
      download(url, bestVideoFormat.get, bestAudioFormat.get)  
    }
    
  }

}
