package mayton.eav

import java.io.{File, FileInputStream}
import java.sql.{Connection, DriverManager}
import java.util.Properties

import scala.collection.mutable

object Utils {

  def createDefaultConnection() : Connection = {
    val props : mutable.Map[String,String] = Utils.toScalaMutableMap(Utils.tryToLoadSensitiveProperties())
    val host       = props("host")
    val port       = props("port")
    val database   = props("database")
    createConnection(s"jdbc:postgresql://$host:$port/$database", props("user"), props("password"))
  }

  def createConnection(url : String, user : String, password : String) : Connection = {
    val props = new Properties
    props.setProperty("user", user)
    props.setProperty("password", password)
    DriverManager.getConnection(url, props)
  }

  def tryToLoadSensitiveProperties() : Properties = {
    val props = new Properties()
    if (new File("sensitive.properties").exists()) {
      props.load(new FileInputStream("sensitive.properties"))
    } else {
      props.put("host",     "localhost")
      props.put("port",     "5432")
      props.put("database", "postgres")
      props.put("user",     "postgres")
      props.put("password", "postgres123")
    }
    props
  }

  def toScalaMutableMap(props : Properties) : mutable.Map[String, String] = {
    var map : mutable.Map[String, String] = new mutable.HashMap[String,String]()
    import scala.jdk.CollectionConverters._
    props.entrySet().asScala.foreach {
      entry => map += ((entry.getKey.asInstanceOf[String], entry.getValue.asInstanceOf[String]))
    }
    map
  }
}
