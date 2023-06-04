import java.time.LocalDate

object DbTool :

  def fromLocalDateToSQLDate(ldt : LocalDate) : java.sql.Date = java.sql.Date.valueOf(ldt)

  def fromSQLDateToLocalDare(sdt : java.sql.Date) : LocalDate = sdt.toLocalDate


