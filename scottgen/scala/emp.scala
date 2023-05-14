
import codegenerator.scott

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

case class Emp(
    empno : Int,
    ename : String,
    job : String,
    mgr : Option[Int],
    hiredate : LocalDate,
    sal : Double,
    comm : Option[Double],
    deptno : Int)

val emps : List[scott.Emp] = List(
  scott.Emp(7369, "SMITH", "CLERK", Some(7902), LocalDate.parse("1980-12-17"), 800.0, None, 20),
  scott.Emp(7499, "ALLEN", "SALESMAN", Some(7698), LocalDate.parse("1981-02-20"), 1600.0, Some(300.0), 30),
  scott.Emp(7521, "WARD", "SALESMAN", Some(7698), LocalDate.parse("1981-02-22"), 1250.0, Some(500.0), 30),
  scott.Emp(7566, "JONES", "MANAGER", Some(7839), LocalDate.parse("1981-04-02"), 2975.0, None, 20),
  scott.Emp(7654, "MARTIN", "SALESMAN", Some(7698), LocalDate.parse("1981-09-28"), 1250.0, Some(1400.0), 30),
  scott.Emp(7698, "BLAKE", "MANAGER", Some(7839), LocalDate.parse("1981-05-01"), 2850.0, None, 30),
  scott.Emp(7782, "CLARK", "MANAGER", Some(7839), LocalDate.parse("1981-06-09"), 2450.0, None, 10),
  scott.Emp(7788, "SCOTT", "ANALYST", Some(7566), LocalDate.parse("1987-04-19"), 3000.0, None, 20),
  scott.Emp(7839, "KING", "PRESIDENT", None, LocalDate.parse("1981-11-17"), 5000.0, None, 10),
  scott.Emp(7844, "TURNER", "SALESMAN", Some(7698), LocalDate.parse("1981-09-08"), 1500.0, Some(0.0), 30),
  scott.Emp(7876, "ADAMS", "CLERK", Some(7788), LocalDate.parse("1987-05-23"), 1100.0, None, 20),
  scott.Emp(7900, "JAMES", "CLERK", Some(7698), LocalDate.parse("1981-12-03"), 950.0, None, 30),
  scott.Emp(7902, "FORD", "ANALYST", Some(7566), LocalDate.parse("1981-12-03"), 3000.0, None, 20),
  scott.Emp(7934, "MILLER", "CLERK", Some(7782), LocalDate.parse("1982-01-23"), 1300.0, None, 10),
)
