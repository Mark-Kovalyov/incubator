package mayton.quintet

case class Quintet(
     id       : Int,
     parent   : Int,
     datatype : Int,
     order    : Int,
     value    : String)

trait QuntetBasicApi {

  def registerPrimitiveType(name : String) : Unit = ???

  def registerEntity(name : String) : Unit = ???

  def quintetById(id : Int) : Quintet = ???

  def quintetChainStartWith(id : Int) : LazyList[Quintet] = ???

  def quintetByDatatypeValue(datatype : Int, value : Option[Int]) : LazyList[Quintet] = ???

  def quintetByParentDatatype(parent : Int, dataType : Option[Int]) : LazyList[Quintet] = ???

}
