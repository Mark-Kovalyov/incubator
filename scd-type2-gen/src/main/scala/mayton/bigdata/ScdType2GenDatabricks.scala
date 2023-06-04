package mayton.bigdata

import java.io.{PrintWriter, Writer}
import java.sql.JDBCType

class ScdType2GenDatabricks extends ScdType2Gen {

  override def namespace(): String = "analytics"

  override def engineName(): String = "databricks"

  override def fromJDBCTypeToNative(jdbctype: java.sql.SQLType): String = jdbctype match {
    case JDBCType.TIME    => "TIMESTAMP"
    case JDBCType.VARCHAR => "STRING"
    case _ => jdbctype.toString
  }

  override def generate(writer: Writer, tableName: String, fields: List[(String, java.sql.SQLType)], primaryKeys: List[String]): Boolean = {
    val pw = new PrintWriter(writer)
    val historyTableName = tableName + "_history"
    val fieldNamesCsv = fields.map((k,v) => k).mkString(",")

    def generateScripts():Unit = {
      pw.println("%sql")
      pw.println(s"select ${fieldNamesCsv} from ${namespace()}.${tableName};")

      pw.println("%sql")
      pw.println(s"select ${fieldNamesCsv} from ${namespace()}.${historyTableName} where is_active = 1;")
    }

    def generateSnapshotTable(): Unit = {
      pw.println("%sql")
      pw.println(s"create table ${namespace()}.${tableName} (")
      pw.println(fields.map((k,v) => "  " + k + " " + fromJDBCTypeToNative(v)).mkString(",\n"))
      pw.println()
      pw.println(") using delta;\n")
    }

    def generateHistoricalTable(): Unit = {
      pw.println("%sql")
      pw.println(s"create table ${namespace()}.${historyTableName} (")
      fields.foreach((fieldName, fieldType) => {
        val stype = fromJDBCTypeToNative(fieldType)
        pw.println(s"  ${fieldName} ${stype},")
      })
      pw.println(s"  start_date ${fromJDBCTypeToNative(JDBCType.TIME)},")
      pw.println(s"  end_date ${fromJDBCTypeToNative(JDBCType.TIME)},")
      pw.println(s"  is_active ${fromJDBCTypeToNative(JDBCType.INTEGER)}")
      pw.println(") using delta;\n")
    }

    generateSnapshotTable()
    generateHistoricalTable()
    generateScripts()
    pw.flush()
    true

  }

}
