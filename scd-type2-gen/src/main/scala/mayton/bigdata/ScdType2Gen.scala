package mayton.bigdata

import java.io.Writer
import scala.collection.mutable

abstract class ScdType2Gen {
  
  def namespace() : String

  def engineName() : String

  def fromJDBCTypeToNative(jdbctype: java.sql.SQLType): String = jdbctype.toString

  /**
   * Generates DDL scripts for SCD Type-2 Architecture
   *
   * @param writer
   * @param tableName
   * @param fields
   * @param primaryKeys
   * @return
   */
  def generate(writer : Writer, tableName : String , fields : List[(String, java.sql.SQLType)], primaryKeys : List[String]) : Boolean

}
