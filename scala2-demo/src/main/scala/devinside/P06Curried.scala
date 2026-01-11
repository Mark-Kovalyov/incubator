package mayton
package devinside

object P06Curried {

  def main(s:Array[String]) : Unit = {

    def add1(a: Int, b: Int, c: Int): Int = a + b + c

    def add2(a: Int): Int => Int => Int = {
      b => c => a + b + c
    }

    def add3(a: Int)(b: Int)(c: Int): Int = a + b + c

    def add4(a: Int)(b: Int): Int => Int = {
      c => a + b + c
    }

    println(add1(100, 10, 1))

    println(add2(100)(10)(1))

    println(add3(100)(10)(1))

    println(add4(100)(10)(1))

  }

}
