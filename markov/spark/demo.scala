package mayton.graphxdemo

import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._
import org.apache.spark.rdd._

object Gx {

  case class Node(v1:String,v2:String,frequency:Int)

  def main(args: Array[String]) : Unit = {

    val spark: SparkSession = SparkSession.builder.getOrCreate()
    val sc = spark.sparkContext

    //val freq : DataFrame = spark.read.option("delimiter", ";").option("header", true).option("inferSchema", true).format("csv").load("/mnt/c/git.bigdata/MarkovDemo/graph-frequencies-2024-07-27-15-29.csv")
    //val graph_df : DataFrame = freq.withColumn("tmp", substring(concat(col("v1"),col("v2")),2,3)).drop("v2").select(col("v1"),col("tmp").alias("v2"),col("frequency"))

    val graph_df: DataFrame = spark.read.format("parquet").load("/mnt/c/db/pass-gan/trigram-markov")
    //graph_df.createOrReplaceTempView("graph_df")

    // 1343124
    val vertices_trigrams_df : DataFrame = graph_df.select(col("v1").alias("v")).unionAll(graph_df.select(col("v2").alias("v"))).distinct().withColumn("id", monotonically_increasing_id()).select("id","v")
    //  166061

    val vertices_rdd : RDD[(VertexId, String)] = vertices_trigrams_df.rdd.map(row => Tuple2(row.getLong(0), row.getString(1)))
    //vertices_rdd.saveAsObjectFile("/mnt/c/db/pass-gan/graph/vertices_rdd")

    val edges_df  : DataFrame = graph_df.join(vertices_trigrams_df, col("v") === col("v1")).drop("v1").drop("v").withColumnRenamed("id","id1")
    val edges_df2 : DataFrame = edges_df.join(vertices_trigrams_df, col("v") === col("v2")).withColumnRenamed("id", "id2").drop("v").drop("v2")
    val edges_rdd : RDD[Edge[Int]] = edges_df2.rdd.map(row => Edge(row.getLong(1), row.getLong(2), row.getInt(0)))
    //edges_rdd.saveAsObjectFile("/mnt/c/db/pass-gan/graph/edges_rdd")

    val graph = Graph(vertices_rdd, edges_rdd)
    graph.numVertices
    graph.numEdges

    val vstart : VertexRDD[String] = graph.vertices.filter { case (id, name) => name == "(((" }
    val vend   : VertexRDD[String] = graph.vertices.filter { case (id, name) => name == ")))" }

  }

}
