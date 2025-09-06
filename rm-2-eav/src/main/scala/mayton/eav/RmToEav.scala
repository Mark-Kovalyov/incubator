package mayton.eav

import java.io.{FileOutputStream, PrintWriter}
import java.sql.Connection

import mayton.quintet.PgQuintetApi

import scala.collection.mutable

object RmToEav {

  def init(connection: Connection, script: PrintWriter): Unit = {
    val api = new PgQuintetApi()
    api.registerPrimitiveType("ROOT")
    api.registerPrimitiveType("SHORT")
    /*
    INSERT INTO quintet VALUES (1,1,1,0,'ROOT');
    INSERT INTO quintet VALUES (3,3,0,0,'SHORT');
    INSERT INTO quintet VALUES (4,4,0,0,'DATETIME');
    INSERT INTO quintet VALUES (8,8,0,0,'CHARS');
    INSERT INTO quintet VALUES (9,9,0,0,'DATE');
    INSERT INTO quintet VALUES (13,13,0,0,'NUMBER');
    INSERT INTO quintet VALUES (14,14,0,0,'SIGNED');
    */
  }

  def migrate(connection: Connection, script: PrintWriter): Unit = {

  }

  def main(arg : Array[String]) : Unit = {

    val script = new PrintWriter(new FileOutputStream("out/migrate.sql"))
    init(connection, script)
    migrate(connection, script)
  }


}
