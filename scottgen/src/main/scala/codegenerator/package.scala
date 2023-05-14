import codegenerator.scott._

package object codegenerator {

  import org.apache.commons.csv.{CSVFormat, CSVParser, CSVRecord}

  import java.io.{FileWriter, PrintWriter}
  import java.nio.file.{Files, Paths}
  import java.time.LocalDate
  import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
  import java.util.Locale
  import java.util.regex.Pattern

  def emp2json(e : Emp) : String = {
    val data = ujson.read("{}")
    data("empno") = e.empno
    data("ename") = e.ename
    data("job") = e.job
    if (e.mgr.isDefined) data("mgr") = e.mgr.get
    data("hiredate") =  DateTimeFormatter.ISO_LOCAL_DATE.format(e.hiredate) + "T00:00:00Z"
    data("sal") = e.sal
    if (e.comm.isDefined) data("comm") = e.comm.get
    data("deptno") = e.deptno
    data.toString()
  }

  val months = Map("JAN" -> 1, "FEB" -> 2, "MAR" -> 3,
    "APR" -> 4, "MAY" -> 5, "JUN" -> 6, "JUL" -> 7, "AUG" -> 8,
    "SEP" -> 9, "OCT" -> 10, "NOV" -> 11, "DEC" -> 12
  )

  def customCsvParser(path : String) : CSVParser = {
    new CSVParser(Files.newBufferedReader(Paths.get(path)),
      CSVFormat.DEFAULT
        .withFirstRecordAsHeader()
        .withIgnoreHeaderCase()
        .withTrim()
        .withDelimiter(';'))
  }

  def processGolang(outFileName : String, records : Iterable[CSVRecord]) : Boolean = {
    val haskell : PrintWriter = new PrintWriter(new FileWriter(outFileName))

    haskell.println("type emp struct{\n" +
      "  empno      int,\n" +
      "  ename      string,\n" +
      "  job        int,\n" +
      "  mgr        int,\n" +
      "  hiredate   string,\n" +
      "  sal        int,\n" +
      "  comm       int,\n" +
      "  deptno     int\n" +
      "} deriving (Show)\n")

    haskell.println("empt = [")
    records.foreach(item => {
      val empno    = item.get("EMPNO")
      val ename    = item.get("ENAME")
      val job      = item.get("JOB")
      val mgr      = item.get("MGR")
      val hiredate = item.get("HIREDATE")
      val sal      = item.get("SAL")
      val comm     = item.get("COMM")
      val deptno   = item.get("DEPTNO")

      haskell.printf(" emp { empno : %s, ename : \"%s\", job : \"%s\", mgr : %s, hiredare : \"%s\", sal : %s, comm : %s, deptno : %s }\n",
        empno,
        ename,
        job,
        mgr,
        hiredate,
        sal,
        comm,
        deptno)
    })
    haskell.print("]")
    haskell.close()
    true
  }

  def processHaskell(outFileName : String, records : Iterable[Emp]) : Boolean = {
    val haskell : PrintWriter = new PrintWriter(new FileWriter(outFileName))
    haskell.println("module Scott (")
    haskell.println(" empt")
    haskell.println(") where\n")
    haskell.println("import Data.Time.Calendar")
    haskell.println("import Data.Maybe\n")
    haskell.println("data codegenerator.scott.Emp = codegenerator.scott.Emp{\n" +
      "  empno      :: Integer,\n" +
      "  ename      :: [Char],\n" +
      "  job        :: [Char],\n" +
      "  mgr        :: Maybe[Integer],\n" +
      "  hiredate   :: Day,\n" +
      "  sal        :: Integer,\n" +
      "  comm       :: Maybe[Integer],\n" +
      "  deptno     :: Integer\n" +
      "} deriving (Show)\n")

    haskell.println("empt = [")

    val pattern = Pattern.compile("(?<day>\\d{2})-(?<mon>[a-zA-Z]{3})-(?<year>\\d{4})")

    val res = records.map(item => {
      val year = "1970"
      val month = "01"
      val day = "01"
      String.format(" codegenerator.scott.Emp { empno = %s, ename = \"%s\", job = \"%s\", mgr = %s, hiredate = %s, sal = %s, comm = %s, deptno = %s }",
        item.empno,
        item.ename,
        item.job,
        if (item.mgr.isEmpty) "Nothing" else "(Just " + item.mgr.get + ")",
        "fromGregorian " + year + " " + month + " " + day,
        item.sal,
        if (item.comm.isEmpty) "Nothing" else "(Just " + item.comm.get + ")",
        item.deptno)
    }).mkString(",\n")

    haskell.println(res)

    haskell.print("]")
    haskell.close()
    true
  }

  def processErlang(outFileName : String, records : Iterable[CSVRecord]) : Boolean = {
    val erlang : PrintWriter = new PrintWriter(new FileWriter(outFileName))
    erlang.println("T0 = gb_trees:empty().")
    var d = 0
    records.foreach(item => {
      val empno    = item.get("EMPNO")
      val ename    = item.get("ENAME")
      val job      = item.get("JOB")
      val mgr      = item.get("MGR")
      val hiredate = item.get("HIREDATE")
      val sal      = item.get("SAL")
      val comm     = item.get("COMM")
      val deptno   = item.get("DEPTNO")

      //printf("D%d = dict:store(id%s, \"%s\", D%d).\n", d + 1, empno, ename, d)
      erlang.printf("T%d = gb_trees:insert(%s, { \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\" }, T%d).\n",
        d + 1,
        empno,
        ename,
        job,
        mgr,
        hiredate,
        sal,
        comm,
        deptno,
        d)
      d = d + 1
    })
    erlang.printf("gb_trees:to_list(T%d).", d)
    erlang.close()
    true
  }

  def processScala(outFileName: String, empList: Iterable[Emp]) : Unit = {
    val scalaWr : PrintWriter = new PrintWriter(new FileWriter(outFileName))

    scalaWr.println(
      """
        |import java.time.LocalDate
        |import java.time.format.DateTimeFormatter
        |import java.util.Locale
        |""".stripMargin)

    scalaWr.println(
      """case class codegenerator.scott.Emp(
        |    empno : Int,
        |    ename : String,
        |    job : String,
        |    mgr : Option[Int],
        |    hiredate : LocalDate,
        |    sal : Double,
        |    comm : Option[Double],
        |    deptno : Int)
        |""".stripMargin)

    scalaWr.println("val emps : List[codegenerator.scott.Emp] = List(")

    empList.foreach(item => {
      scalaWr.print("  codegenerator.scott.Emp(")
      scalaWr.printf("%s, \"%s\", ", item.empno, item.ename)
      scalaWr.printf("\"%s\", %s, ", item.job, if (item.mgr.isEmpty) "None" else "Some(" + item.mgr.get + ")" )
      scalaWr.printf("LocalDate.parse(\"%s\"), %s.0, ", item.hiredate, item.sal)
      scalaWr.printf("%s, %s", if(item.comm.isEmpty) "None" else "Some(" + item.comm.get + ".0)", item.deptno)
      scalaWr.println("),")
    })
    scalaWr.println(")")
    scalaWr.close
  }

  def processJava(outFileName: String, empList: Iterable[Emp]) : Unit = {
    val wr : PrintWriter = new PrintWriter(new FileWriter(outFileName))
    wr.println(
      """import jdk.nashorn.internal.ir.annotations.Immutable;
        |import java.time.LocalDate;
        |import java.util.Optional;
        |
        |@Immutable
        |public class Emp2 {
        |
        |    public final int empno;
        |    public final String ename;
        |    public final String job;
        |    public final Optional<Integer> mgr;
        |    public final LocalDate hiredate;
        |    public final double sal;
        |    public final Optional<Double> comm;
        |    public final int deptno;
        |
        |    public Emp2(int empno, String ename, String job, Optional<Integer> mgr, LocalDate hiredate, double sal, Optional<Double> comm, int deptno) {
        |        this.empno = empno;
        |        this.ename = ename;
        |        this.job = job;
        |        this.mgr = mgr;
        |        this.hiredate = hiredate;
        |        this.sal = sal;
        |        this.comm = comm;
        |        this.deptno = deptno;
        |    }
        |}
        |
        |List<Emp2> emps = new ArrayList() {{""".stripMargin)
    empList.foreach(item => {
      wr.printf("  add(new Emp2(%s, \"%s\", \"%s\", %s",
        item.empno,
        item.ename,
        item.job,
        if (item.mgr.isDefined) "Optional.of(" + item.mgr.get + ")" else "Optional.empty()")
      wr.printf(", LocalDate.parse(\"%s\"), %s.0, ", item.hiredate, item.sal)
      wr.printf("%s, %s", if(item.comm.isEmpty) "Optional.empty()" else "Optional.of(" + item.comm.get + ".0)", item.deptno)
      wr.println("));")
    });
    wr.println("}}")
    wr.close()
  }

  def processElixir(outFileName: String, empList: Iterable[Emp]) : Unit = {
    val elixir : PrintWriter = new PrintWriter(new FileWriter(outFileName))
    elixir.printf("list1 = [")
    empList.foreach(item => {
      elixir.print("{")
      elixir.print(s"${item.empno},${item.ename},${item.empno},${item.job},${item.mgr.getOrElse("nil")},")
      val dateTime = "DateTime.from_iso8601(\"" + item.hiredate + "T00:00:00Z\")"
      elixir.print(s"${dateTime}, ${item.comm.getOrElse("nil")}, ${item.deptno}")
      elixir.println("},")
    })
    elixir.printf("]")
    elixir.close
  }



  import scala.jdk.CollectionConverters._

  def empCollection() : Iterable[CSVRecord] = customCsvParser("csv/scott/emp.csv").getRecords.asScala
  def deptCollection(): Iterable[CSVRecord] = customCsvParser("csv/scott/dept.csv").getRecords.asScala

  val formatter : DateTimeFormatter = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendPattern("dd-MMM-yyyy")
    .parseLenient()
    .toFormatter(Locale.ENGLISH)

  def empList() : List[Emp] = empCollection()
    .map(x => Emp(x.get("EMPNO"),
      x.get("ENAME"),
      x.get("JOB"),
      if (x.get("MGR") == "") None else Some(x.get("MGR")),
      LocalDate.parse(x.get("HIREDATE"), formatter),
      x.get("SAL"),
      if (x.get("COMM") == "") None else Some(x.get("COMM")),
      x.get("DEPTNO"))).toList

  def deptList() : List[Dept] = deptCollection()
    .map(x => Dept(
      x.get("DEPTNO").toInt,
      x.get("DNAME"),
      x.get("LOC"))).toList




}
