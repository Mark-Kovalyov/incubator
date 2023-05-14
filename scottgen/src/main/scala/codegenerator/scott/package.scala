package codegenerator

import codegenerator.scott.{Dept, Emp}

import java.io.{File, FileWriter, PrintWriter}
import java.nio.file.{Files, Path}
import java.time.format.DateTimeFormatter
import scala.util.Using

package object scott {

  case class Emp(
                  empno: String,
                  ename: String,
                  job: String,
                  mgr: Option[String],
                  hiredate: java.time.LocalDate,
                  sal: String,
                  comm: Option[String],
                  deptno: String) {}

  case class Dept(
                   deptno : Int,
                   dname : String,
                   loc : String) {}

  trait CodeGen {
    def emp() : Unit
    def dept() : Unit
  }

  class CodegenBase(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodeGen {
    override def emp(): Unit = {}
    override def dept(): Unit = {}
    def go() : Unit = {
      Files.createDirectories(Path.of(basePath))
      emp()
      dept()
    }
  }

  class JsonLevel2Gen(basePath: String, empList: List[Emp], deptList: List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def emp(): Unit = {
      import com.fasterxml.jackson.core._
      import com.fasterxml.jackson.databind._
      import com.fasterxml.jackson.core.util._

      val mapper = new ObjectMapper()
      val p: PrintWriter = new PrintWriter(new FileWriter(basePath + "/emplevel2.json"))
      val jg: JsonGenerator = mapper.getFactory.createGenerator(p)
      jg.writeStartArray()
      deptList.foreach(dept => {
        jg.writeStartObject()
        jg.writeNumberField("deptno", dept.deptno)
        jg.writeFieldName("emps")
        jg.writeStartArray()
          empList.filter(x => x.deptno == dept.deptno.toString).foreach(emp => {
            jg.writeStartObject()
            jg.writeStringField("empno", emp.empno)
            jg.writeStringField("ename", emp.ename)
            jg.writeStringField("job", emp.job)
            jg.writeNumberField("sal", emp.sal.toDouble)
            jg.writeStringField("deptno", emp.deptno)
            if (emp.comm.isDefined) jg.writeNumberField("comm", emp.comm.get.toDouble)
            jg.writeStringField("hiredate", emp.hiredate.format(DateTimeFormatter.ISO_LOCAL_DATE))
            jg.writeEndObject()
          })
        jg.writeEndArray()
        jg.writeEndObject()
      })
      jg.writeEndArray()
      jg.close()
    }
  }

  class JsonGen(basePath: String, empList: List[Emp], deptList: List[Dept]) extends CodegenBase(basePath, empList, deptList) {

    import com.fasterxml.jackson.core._
    import com.fasterxml.jackson.databind._
    import com.fasterxml.jackson.core.util._

    override def emp(): Unit = {

      val mapper = new ObjectMapper()
      val p: PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.json"))
      val jg : JsonGenerator = mapper.getFactory.createGenerator(p)
      jg.writeStartArray()
      empList.foreach(emp => {
        jg.writeStartObject()
        jg.writeStringField("empno",emp.empno)
        jg.writeStringField("ename",emp.ename)
        jg.writeStringField("job",emp.job)
        jg.writeNumberField("sal", emp.sal.toDouble)
        jg.writeStringField("deptno",emp.deptno)
        if (emp.comm.isDefined) jg.writeNumberField("comm",emp.comm.get.toDouble)
        jg.writeStringField("hiredate",emp.hiredate.format(DateTimeFormatter.ISO_LOCAL_DATE))
        jg.writeStringField("depno", emp.deptno)
        jg.writeEndObject()
      })
      jg.writeEndArray()
      jg.close()
    }

    override def dept(): Unit = {

      val mapper = new ObjectMapper()
      val p: PrintWriter = new PrintWriter(new FileWriter(basePath + "/dept.json"))
      val jg: JsonGenerator = mapper.getFactory.createGenerator(p)
      jg.writeStartArray()
      deptList.foreach(dept => {
        jg.writeStartObject()
        jg.writeNumberField("empno", dept.deptno)
        jg.writeStringField("loc", dept.loc)
        jg.writeStringField("dname", dept.dname)
        jg.writeEndObject()
      })
      jg.writeEndArray()
      jg.close()
    }
  }

  class PySparkGen(basePath: String, empList: List[Emp], deptList: List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def emp(): Unit = {
      val sp: PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.py"))
      sp.println(
        """
          |import spark.implicits._
          |import org.apache.spark.sql.types.{DataTypes, DateType, DoubleType, IntegerType, StringType, StructField, StructType}
          |import org.apache.spark.sql.Row
          |
          |schema = StructType(Array(
          |    StructField("empno",    IntegerType),
          |    StructField("ename",    StringType),
          |    StructField("job",      StringType),
          |    StructField("mgr",      IntegerType),
          |    StructField("hiredate", StringType),
          |    StructField("sal",      DoubleType),
          |    StructField("comm",     DoubleType),
          |    StructField("depno",    IntegerType)
          |))
          |
          |""".stripMargin)

      sp.print("val data = Seq(")
      var isFirst = true
      empList.foreach(emp => {
        sp.printf(if (isFirst) "\n" else ",\n")
        sp.printf("  Row(%s,\"%s\",\"%s\", %s, \"%s\", %s, %s, %s)",
          emp.empno,
          emp.ename,
          emp.job,
          emp.mgr.getOrElse("null"),
          emp.hiredate,
          emp.sal + ".0",
          if (emp.comm.isDefined) emp.comm.get + ".0" else "null",
          emp.deptno)
        isFirst = false
      })
      sp.println(
        """)
          |
          |val rdd = spark.sparkContext.parallelize(data)
          |
          |val df = spark.createDataFrame(rdd, schema)
          |
          |df.show()
          |""".stripMargin)
      sp.close()
    }
  }

  class SparkSqlGen(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def emp(): Unit = {
      val sp: PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.sql"))

      sp.println()
      sp.close()
    }
  }

  class SparkGen(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def emp(): Unit = {
        val sp : PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.scala"))
        sp.println(
          """
            |import spark.implicits._
            |import org.apache.spark.sql.types.{DataTypes, DateType, DoubleType, IntegerType, StringType, StructField, StructType}
            |import org.apache.spark.sql.Row
            |import java.time.LocalDate
            |import java.sql.Date
            |
            |val schema = StructType(Array(
            |    StructField("empno",    IntegerType),
            |    StructField("ename",    StringType),
            |    StructField("job",      StringType),
            |    StructField("mgr",      IntegerType),
            |    StructField("hiredate", DateType),
            |    StructField("sal",      DoubleType),
            |    StructField("comm",     DoubleType),
            |    StructField("depno",    IntegerType)
            |))
            |
            |""".stripMargin)

        sp.print("val data = Seq(")
        var isFirst = true
        empList.foreach(emp => {
          sp.printf(if (isFirst) "\n" else ",\n")
          sp.printf("  Row(%s,\"%s\",\"%s\", %s, Date.valueOf(LocalDate.parse(\"%s\")), %s, %s, %s)",
            emp.empno,
            emp.ename,
            emp.job,
            emp.mgr.getOrElse("null"),
            emp.hiredate,
            emp.sal + ".0",
            if (emp.comm.isDefined) emp.comm.get + ".0" else "null",
            emp.deptno)
          isFirst = false
        })
        sp.println(
          """)
            |
            |val rdd = spark.sparkContext.parallelize(data)
            |
            |val df = spark.createDataFrame(rdd, schema)
            |
            |df.createOrReplaceTempView("emp")
            |
            |df.show()
            |""".stripMargin)
        sp.close()
    }
  }

  class CouchDbGen(basePath : String, empList : List[Emp], deptList : List[Dept], host : String, port : Int) extends CodegenBase(basePath, empList, deptList) {

    def emp2json2(e : Emp) : String = {
      s"{ \"empno\" : ${e.empno}, \"ename\" : \"${e.ename}\", \"job\" : \"${e.job}\", \"mgr\" : \"${e.mgr.getOrElse("")}\", \"sal\" : ${e.sal} }"
    }

    override def emp(): Unit = {
      Using.Manager { use =>
        val couch : PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.sh"))
        couch.println("#!/bin/bash -v")
        couch.println(s"curl -X PUT http://${host}:${port}/scott")
        empList.foreach(emp =>
          couch.println(s"curl -H 'Content-Type: application/json' -X PUT http://${host}:${port}/scott/${emp.empno} -d '${emp2json2(emp)}'")
        )
        couch.close()
      }
    }
  }

  class PgGen(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodegenBase(basePath, empList, deptList) {

    override def dept(): Unit = {
      Using.Manager { use =>
        val pgw : PrintWriter = new PrintWriter(new FileWriter(basePath + "/dept.sql"))

        pgw.println(
          """create table dept(
            |deptno integer,
            |dname string,
            |loc string);
            |  """.stripMargin)

        deptList.foreach(item => {

          pgw.printf("insert into dept values(%s,'%s','%s');\n",
            item.deptno,
            item.dname,
            item.loc
          )
        })
        pgw.close()
      }
    }

    override def emp(): Unit = {
      Using.Manager { use =>
        val pgw : PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.sql"))

        pgw.println(
          """create table emp(
            |  empno integer primary key,
            |  ename text,
            |  job text,
            |  mgr integer,
            |  hiredate date,
            |  sal decimal,
            |  comm decimal,
            |  depno integer);
            |  """.stripMargin)

        pgw.println("SET datestyle TO \"ISO, DMY\";")

        empList.foreach(row => {
          val empno = row.empno
          val ename = row.ename
          val job = row.job
          val mgr = row.mgr
          val hiredate = row.hiredate
          val sal = row.sal
          val comm = row.comm

          pgw.printf("insert into emp(empno,ename,job,mgr,hiredate,sal,comm,depno) values(%s,'%s','%s', %s, TIMESTAMP '%s', %s, %s, %s);\n",
            empno,
            ename,
            job,
            mgr.getOrElse("null"),
            hiredate,
            sal,
            comm.getOrElse("null"),
            row.deptno)
        })
        pgw.close()
      }
    }
  }

  class Neo4jGen(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def go(): Unit = {
      Using.Manager { use =>
        val pgw: PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.groovy"))

        empList.foreach(row => {
          pgw.println(s"v_emp_${row.empno} = g1.addVertex(`[empno = ${row.empno}, ename = ${row.ename}]`)")
        })

        pgw.println()

        empList.map(x => x.job).distinct.foreach(job => {
          pgw.println(s"v_job_${job} = g1.addVertex(`[job = ${job}]`)")
        })

        pgw.println()

        deptList.foreach(dept => {
          pgw.println(s"v_dept_${dept.deptno} = g1.addVertex(`[deptno = ${dept.deptno}, loc = ${dept.loc}, dname = ${dept.dname}]`)")
        })

        pgw.println()

        var ecnt = 0
        empList.foreach(row => {
          pgw.println(s"e${ecnt} = g1.addEdge(v1, v2, `has_job`)")
          ecnt = ecnt + 1
          pgw.println(s"e${ecnt} = g1.addEdge(v1, v2, `work_in_dept`)")
          ecnt = ecnt + 1
        })

        pgw.close()
      }
    }
  }

  class DatabricksGen(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def emp(): Unit = {
      Using.Manager { use =>
        val pgw : PrintWriter = new PrintWriter(new FileWriter(basePath + "/emp.sql"))

        pgw.println("drop table if exists emp;")

        pgw.println()

        pgw.println(
          """create table if not exists emp(
            |  empno integer,
            |  ename string,
            |  job string,
            |  mgr integer,
            |  hiredate date,
            |  sal decimal,
            |  comm decimal,
            |  depno integer) using parquet
            |  partitioned by(depno);
            |  """.stripMargin)

        empList.foreach(row => {
          val empno = row.empno
          val ename = row.ename
          val job = row.job
          val mgr = row.mgr
          val hiredate = row.hiredate
          val sal = row.sal
          val comm = row.comm

          pgw.printf("insert into emp(empno,ename,job,mgr,hiredate,sal,comm,depno) values(%s,'%s','%s', %s, TIMESTAMP '%s', %s, %s, %s);\n",
            empno,
            ename,
            job,
            mgr.getOrElse("null"),
            hiredate,
            sal,
            comm.getOrElse("null"),
            row.deptno)
        })
        pgw.close()
      }
    }
  }

  class RedisGen(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def emp(): Unit = {
      Using.Manager { use =>
        val pw = new PrintWriter(basePath + "/emp")
        empList.foreach(row => {
          pw.print(s"HMSET emp:${row.empno} ename \"${row.ename}\" job \"${row.job}\"")
          if (row.mgr.isDefined) pw.print(s" mgr \"${row.mgr.get}\"")
          val hiredateIsoString = DateTimeFormatter.ISO_LOCAL_DATE.format(row.hiredate) + "T00:00:00Z"
          pw.print(s" hiredate \"${hiredateIsoString}\"")
          pw.print(s" sal ${row.sal}")
          if (row.comm.isDefined) pw.print(s" comm \"${row.comm.get}\"")
          pw.print(s" deptno ${row.deptno}")
          pw.println()
        })
        pw.close()
      }
    }
    override def dept(): Unit = super.dept()
  }

  // https://learnyousomeerlang.com/mnesia
  class ErlangMnesiaGen(basePath : String, empList : List[Emp], deptList : List[Dept]) extends CodegenBase(basePath, empList, deptList) {
    override def emp(): Unit = {
      Using.Manager { use =>
        val pw = new PrintWriter(basePath + "/emp.erl")
        pw.println(
          """-module(scott).
            |-export([install/1]).
            |
            |-record(emp).
            |
            |install(Nodes) ->
            |  ok = mnesia:create_schema(Nodes),
            |  application:start(mnesia),
            |  mnesia:create_table(emp),
            |  application:stop(mnesia).
            |""".stripMargin)
        pw.close()
      }
    }
    override def dept(): Unit = {
    }
  }



}
