package mayton.quintet

import java.sql.Connection

object PgQuintetApi extends QuntetBasicApi {

  val conn : Connection = mayton.eav.Utils.createDefaultConnection()

  override def registerPrimitiveType(name: String): Unit = {
    val sql = s"INSERT INTO quintet VALUES (1,1,1,0,${name})"

  }

  override def registerEntity(name: String): Unit = super.registerEntity(name)

  override def quintetById(id: Int): Quintet = super.quintetById(id)

  override def quintetChainStartWith(id: Int): LazyList[Quintet] = super.quintetChainStartWith(id)

  override def quintetByDatatypeValue(datatype: Int, value: Option[Int]): LazyList[Quintet] = super.quintetByDatatypeValue(datatype, value)

  override def quintetByParentDatatype(parent: Int, dataType: Option[Int]): LazyList[Quintet] = super.quintetByParentDatatype(parent, dataType)
}
