package mayton
package devinside

object P07PartialFun {

  def main(s:Array[String]) : Unit = {
    val pf : PartialFunction[Boolean,String] = {
      case true => Console.GREEN + "good" + Console.RESET
    }

    print(pf(true))

    print(pf(false)) // Produces Exception in thread "main" scala.MatchError: false (of class java.lang.Boolean)
  }

}
