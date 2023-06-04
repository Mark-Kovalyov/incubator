package mayton.bigdata

import java.io.{PrintWriter, Writer}
import java.sql.JDBCType

class ScdType2GenPg extends ScdType2Gen {

  override def namespace(): String = "analytics"

  override def engineName(): String = "pg"

  override def fromJDBCTypeToNative(jdbctype: java.sql.SQLType): String = jdbctype match {
    case JDBCType.TIME    => "TIMESTAMP"
    case JDBCType.VARCHAR => "TEXT"
    case _                => jdbctype.toString
  }

  override def generate(writer: Writer, tableName: String, fields: List[(String, java.sql.SQLType)], primaryKeys: List[String]): Boolean = {

    val pw = new PrintWriter(writer)

    def generateStoredProcedures() : Unit = {
      pw.println(
        """
          |CREATE PROCEDURE update_history(a integer, b integer)
          |LANGUAGE SQL
          |AS $$
          |  SELECT * FROM history WHERE 
          |  INSERT INTO tbl VALUES (b);
          |$$;
          |""".stripMargin)
    }

    def generateSnapshotTable(): Unit = {
      pw.println(s"create table ${tableName} (")
      fields.foreach((fieldName, fieldType) => {
        val stype = fromJDBCTypeToNative(fieldType)
        pw.println(s"  ${fieldName} ${stype},")
      })
      pw.println(")\n")
    }

    def generateHistoricalTable(): Unit = {
      pw.println(s"create table ${tableName}_history (")
      fields.foreach((fieldName, fieldType) => {
        val stype = fromJDBCTypeToNative(fieldType)
        pw.println(s"  ${fieldName} ${stype},")
      })
      pw.println(s"  start_date ${fromJDBCTypeToNative(JDBCType.TIME)},")
      pw.println(s"  end_date ${fromJDBCTypeToNative(JDBCType.TIME)},")
      pw.println(s"  is_active ${fromJDBCTypeToNative(JDBCType.INTEGER)}")
      pw.println(")\n")
    }

    generateSnapshotTable()
    generateHistoricalTable()
    generateStoredProcedures()
    pw.flush()
    true
  }
}
