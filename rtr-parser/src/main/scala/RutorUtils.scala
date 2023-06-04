import java.time.{DateTimeException, LocalDate}
import java.util.Locale
import scala.util.matching.Regex

object RutorUtils :

  def extractId(path: String): Option[String] =
    val idPattern: Regex = "(.+)/(\\d+)".r
    path match
      case idPattern(_, right) => Some(right)
      case _ => None

  def extractDate(expr: String): Option[LocalDate] =
    val cleaned = expr.toLowerCase().replaceAll(Regex.quote("&nbsp;"), " ")
    val monthMap = Map(
      "янв" -> 1,
      "фев" -> 2,
      "мар" -> 3,
      "апр" -> 4,
      "май" -> 5,
      "июн" -> 6,
      "июл" -> 7,
      "авг" -> 8,
      "сен" -> 9,
      "окт" -> 10,
      "ноя" -> 11,
      "дек" -> 12
    )
    val idPattern: Regex = "(\\d{1,2}) (\\S{3}) (\\d{1,2})".r
    cleaned match
      case idPattern(sday, smonth, syear) =>
        try
          val year = syear.toInt + 2000
          val month = monthMap(smonth)
          val day = sday.toInt
          Some(LocalDate.of(year, month, day))
        catch
          case ex: DateTimeException =>
            println(ex.toString)
            None
      case _ => None


