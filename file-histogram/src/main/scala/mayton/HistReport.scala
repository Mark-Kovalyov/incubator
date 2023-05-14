package mayton

import java.time.LocalDateTime

case class HistReport( ts : LocalDateTime, 
                       tag : String,
                       hash : String,
                       rows : List[HistReportEntity])
