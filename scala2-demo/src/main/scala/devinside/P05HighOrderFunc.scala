package mayton
package devinside

object P05HighOrderFunc {

  def main(s:Array[String]) : Unit = {

    def sortListOfInts(ints : List[Int], sortFunction : (Int,Int) => Boolean) : List[Int] = {
      ints.sortWith(sortFunction)
    }

    def sortMethod(asc: Boolean)(a:Int, b:Int) : Boolean = {
      if (asc) a < b else a > b
    }

    println(sortListOfInts(List(6,3,1,8,2), sortMethod(asc = true)))

  }

}
