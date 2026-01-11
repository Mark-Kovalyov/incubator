package mayton
package devinside

object P03Lambdas {

  def main(s:Array[String]) : Unit = {

    val age:Int = 36
    val f: Int => Int = input => input + 1
    // Underscore
    val f2: Int => Int = _ + 1

    /*val f3: Int => Int = Function1[Int, Int] {
      override def apply(input: Int) : Int = input + 1
    }*/

    println(age)

    println(f(35))
    println(f2(35))

    println(f(age))

    val sort1: (Int,Int) => Boolean = (a,b) => a < b;

    println(List(3,1,2).sortWith(sort1))


  }

}
