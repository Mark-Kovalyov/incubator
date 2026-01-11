package mayton
package devinside

object P02StringInterp {

  def main(s:Array[String]) : Unit = {
    val left  : Seq[Int] = Seq(1,2,3) :+ 4
    val right : Seq[Int] = 0 +: Seq(1,2,3)

    println(left)
    println(right)

    val s:String   = s"Max int\n${Int.MaxValue}"
    val f:String   = f"Max int\n${Int.MaxValue}%40d"
    val raw:String = raw"Max int\n${Int.MaxValue}"

    println(s)
    println(f)
    println(raw)
  }

}
