package mayton.chess

import scala.collection.mutable.ListBuffer
import java.time.Duration
import java.time.Instant
import java.lang.Math._

/**
  * See:
  *  Queen problem
  *  --------------
  *  https://en.wikipedia.org/wiki/Eight_queens_puzzle
  *  http://claymath.org/events/news/8-queens-puzzle
  *  http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.51.7113&rep=rep1&type=pdf
  *  http://www.ic-net.or.jp/home/takaken/e/queen/
  *
  *  Scala & performance
  *  -------------------
  *  http://www.lihaoyi.com/post/BenchmarkingScalaCollections.html
  */
object QueenProblem {

  def rotateRight(positions : List[Pos]) : List[Pos] = {
    // TODO: Implement
    null
  }

  def swapHorizontal(positions : List[Pos]) : List[Pos] = {
    // TODO: Implement
    null
  }





  // TODO: Fix
  def isCan(q: List[Int], size : Int) : Boolean = {
    // TODO: Transform list of integer to Positions
    var arr = new Array[Pos](size)

    // TODO: Rotate 4 times rigth, mirror vertical, horizontal, diagonal
    //
    true
  }

  def printSol(q: List[Int], level: Int): Unit = {
    var plsHold = ""
    if (indsol) {
      plsHold = formOffs(level, ' ')
      print(plsHold)
    }
    println(formQ(q))
    if (pd) {
      println(formOffs(level, '-'))
      val n = q.size
      for ( i <- q ) {
        var j = 0
        while ( j < n ) {
          print(if (i == j) "Q" else "*")
          print(" ")
          j = j + 1
        }
        println
      }
      println
    }
    psol += 1
    if (sa && psol >= solth) {
      System.exit(0)
    }
  }

  def isCons(q: List[Int]): Boolean = {
    // TODO: Analyze for O(n), O(n)
    isCons(q.take(q.size - 1), q.last)
  }

  def isCons(q: List[Int], c: Int): Boolean = {
    val n = q.size
    var i = 0
    while (i < n) {
      // TODO: Analyze for O(n)
      if (abs(q(i) - c) == (n - i)) return false
      i = i + 1
    }
    true
  }

  def formQ(q: List[Int]): String = "[" + q.mkString(", ") + "]"

  def formOffs(n: Int, c: Char): String = {
    val sb = new StringBuilder
    for( i <- 0 until n) sb.append(c)
    sb.toString
  }

  def process(lev: Int, cndd: List[Int]): Unit = {
    rpc += 1
    if (trecarg) {
      printf("%s %s %s \n",
        formOffs(lev, '.'),
        formQ(cndd),
        formQ(sel.toList))
    }
    if (sel.size == n) {
      if (isCons(sel.toList)) {
        sol += 1
        if (ckcan) {
          if (isCan(sel.toList, n)) {
            solcan += 1
            printSol(sel.toList, lev)
          }
        }
        printSol(sel.toList, lev)
      }
    } else {
      val a = new ListBuffer[Int]()
      val b = new ListBuffer[Int]()
      b.appendAll(cndd)
      while ( b.nonEmpty ) {
        val ncndd = b.remove(0)
        if (isCons(sel.toList, ncndd)) {
          sel += ncndd
          // TODO: Analyze for O(n) during concatenation
          process(lev + 1, a.toList ++ b.toList)
          sel.trimEnd(1)
        }
        a += ncndd
      }
    }
  }

  var sol:Int   = 0
  var psol:Int  = 0
  val solth:Int = 100
  var n:Int     = 0
  var ckcan = false
  var solcan:Int = 0
  var sel:ListBuffer[Int] = new ListBuffer[Int]()
  var rpc:Int        = 0
  var trecarg        = false
  val pd             = false
  val indsol         = false
  val pr             = true
  val sa             = false

  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Usage: java -jar QueenProblem-XX.YYYY.jar DESK_SIZE [CHECK_CANONICAL]")
      println("Where:")
      println("     DESK_SIZE       : size of desk (4,5,6, e.t.c)")
    }
    else {
      val start = Instant.now
      n = args(0).toInt
      if (args.length > 1) {
        ckcan = if (args(0) == "Y") true else false
      }
      process(0, (0 until n).toList)
      val end = Instant.now
      println("=======================================================")
      printf(s"Solutions           : $sol\n")
      printf("Canonical solutions : %s\n", if (!ckcan) "Unknown" else "" + solcan)
      val elapsed = Duration.between(start, end).toMillis
      printf("Elapsed time   : $elapsed ms\n")
      printf("AVG speed      : %d solutions/s\n", 1000L * sol / elapsed)
    }
  }


}
