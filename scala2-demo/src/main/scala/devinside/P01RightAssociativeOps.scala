package mayton
package devinside

object P01RightAssociativeOps {

  final case class Person(name:String, age:Int) {
    def +(years:Int) : Person = copy(age = age + years)
    //infix def plus(years:Int) : Person = copy(age = age + years)
  }

  def main(s:Array[String]) : Unit = {
    println(Person("Enot", 35) + 1)
    println(Person("Enot", 35).+(1))
  }

}
