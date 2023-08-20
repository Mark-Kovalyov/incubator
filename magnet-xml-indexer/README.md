# CSV index

```scala
val books = spark.read.option("header", "true").option("inferSchema", "true").option("delimiter",";").csv("/bigdata/documents.idx/documents2023-08-19-22-53-26.866029213.csv")
df.createOrReplaceTempView("books")

//df.coalesce(1).write.mode("overwrite").parquet("/bigdata/documents.idx/documents-par")
//df.coalesce(1).write.mode("overwrite").orc("/bigdata/documents.idx/documents-orc")
```

## Magnet SHA1 (Doenst works with Transmission)

```scala
df.createOrReplaceTempView("books")

spark.sql("select 'magnet:?dn='||name||'&xt=urn:sha1:'||sha1||'&xl='||size from books where lower(name) like '%assemb%'").show(false);
+---------------------------------------------------------------------------------------+--------------------------------+
|name                                                                                   |sha1                            |
+---------------------------------------------------------------------------------------+--------------------------------+
|Mechanical Assembly.pdf                                                                |d34ce9c89a02e6eb8c1a9b38020cedb6|
|Britton -- MIPS Assembly Programming Language -- 2004.pdf                              |2b2adac288a8020371f42ebe86f6ec06|
|Сван -- Освоение Turbo Assembler -- 1996.pdf                                           |f2716810f6d6da19be4acc9a59b1b069|
```

## Magnet BTIH (Bittorrent Info Hash)


```scala
spark-shell -cp ~/.m2/repository/com/google/guava/guava/32.0.0-jre/guava-32.0.0-jre.jar

import spark.implicits._
import com.google.common.net._

val url_encode_udf = udf((url:String) => UrlEscapers.urlFragmentEscaper().escape(url))

spark.udf.register("url_encode", url_encode_udf)

spark.sql("select 'magnet:?dn='||url_encode(name)||'&xt=urn:btih:'||btih||'&xl='||size from books where lower(name) like '%assemb%'").show(false);
```

```scala
books
  .filter("lower(name) like '%assem%'")
  .filter("name like '%20%'")
  .map(row => Tuple1(row.getString(0), row.getInt(1)))
  .show()
```
