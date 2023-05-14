package codegenerator

import codegenerator.*
import codegenerator.scott.*

import java.nio.file.{Files, Path}

object Main {

  def main(args : Array[String]) : Unit = {

    import codegenerator.scott._

    val collection : List[CodegenBase] = List(
      new ErlangMnesiaGen("nosql/mnesia", empList(), deptList()),
      new RedisGen("nosql/redis", empList(), deptList()),
      new CouchDbGen("nosql/couchdb", empList(), deptList(), "192.168.1.2", 5555),
      new PgGen("relational/pg", empList(), deptList()),
      new DatabricksGen("relational/databricks", empList(), deptList()),
      new Neo4jGen("nosql/neo4j", empList(), deptList()),
      new SparkGen("bigdata/spark", empList(), deptList()),
      new SparkSqlGen("bigdata/spark-sql", empList(), deptList()),
      new PySparkGen("bigdata/spark", empList(), deptList()),
      new JsonGen("json", empList(), deptList()),
      new JsonLevel2Gen("json", empList(), deptList())
    )

    collection.foreach(item => item.go())

  }
}
