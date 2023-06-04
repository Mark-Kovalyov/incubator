package mayton.bigdata

import java.io.{File, FileWriter}
import java.sql._

object ScdType2GenMain {

  def main(args: scala.Array[String]): Unit = {

    val engines = List[ScdType2Gen](new ScdType2GenPg(), new ScdType2GenDatabricks())

    for(engine <- engines) {
      new File("out/" + engine.engineName()).mkdirs()
      val writer = new FileWriter(s"out/${engine.engineName()}/emp-model.sql")
      engine.generate(
        writer,
        "emp",
        List(
          ("empno", JDBCType.INTEGER),
          ("ename", JDBCType.VARCHAR),
          ("job", JDBCType.VARCHAR),
          ("mgr", JDBCType.INTEGER),
          ("hiredate", JDBCType.DATE),
          ("sal", JDBCType.DECIMAL),
          ("comm", JDBCType.DECIMAL),
          ("depno", JDBCType.INTEGER)
        ),
        List("empno")
      )
      writer.close()
    }


  }
}
