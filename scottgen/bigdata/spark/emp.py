
import spark.implicits._
import org.apache.spark.sql.types.{DataTypes, DateType, DoubleType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.Row

schema = StructType(Array(
    StructField("empno",    IntegerType),
    StructField("ename",    StringType),
    StructField("job",      StringType),
    StructField("mgr",      IntegerType),
    StructField("hiredate", StringType),
    StructField("sal",      DoubleType),
    StructField("comm",     DoubleType),
    StructField("depno",    IntegerType)
))


val data = Seq(
  Row(7369,"SMITH","CLERK", 7902, "1980-12-17", 800.0, null, 20),
  Row(7499,"ALLEN","SALESMAN", 7698, "1981-02-20", 1600.0, 300.0, 30),
  Row(7521,"WARD","SALESMAN", 7698, "1981-02-22", 1250.0, 500.0, 30),
  Row(7566,"JONES","MANAGER", 7839, "1981-04-02", 2975.0, null, 20),
  Row(7654,"MARTIN","SALESMAN", 7698, "1981-09-28", 1250.0, 1400.0, 30),
  Row(7698,"BLAKE","MANAGER", 7839, "1981-05-01", 2850.0, null, 30),
  Row(7782,"CLARK","MANAGER", 7839, "1981-06-09", 2450.0, null, 10),
  Row(7788,"SCOTT","ANALYST", 7566, "1987-04-19", 3000.0, null, 20),
  Row(7839,"KING","PRESIDENT", null, "1981-11-17", 5000.0, null, 10),
  Row(7844,"TURNER","SALESMAN", 7698, "1981-09-08", 1500.0, 0.0, 30),
  Row(7876,"ADAMS","CLERK", 7788, "1987-05-23", 1100.0, null, 20),
  Row(7900,"JAMES","CLERK", 7698, "1981-12-03", 950.0, null, 30),
  Row(7902,"FORD","ANALYST", 7566, "1981-12-03", 3000.0, null, 20),
  Row(7934,"MILLER","CLERK", 7782, "1982-01-23", 1300.0, null, 10))

val rdd = spark.sparkContext.parallelize(data)

val df = spark.createDataFrame(rdd, schema)

df.show()

