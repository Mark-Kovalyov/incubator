/**
  *
  * Port of Gennady Usov algotirhm from http://www.sql.ru/forum/1273790-31/rasstanovka-ferzey
  *
  */
object QueenProblem {

  def not(x : Boolean) : Boolean = !x

  def остаток(a : Int, b : Int): Int = a % b

  def нечетное(n : Int) : Boolean = n % 2 == 1

  // Применим на досках N = 6хК+К1, где К1 = 2.

  def main(args: Array[String]): Unit = {
    val N = 14
    var MR = Array.ofDim[Int](N + 1, N + 1)
    val К1 = остаток(N , 6)
    val K = (N - К1) / 6
    if (нечетное(K)) {
      val R2 = 6 // количество решений
      val m1 = 0
      val m2 = 2
      val n2 = N / 2
      for(i <- 1 to N) {
        var m3 = 0
        var j = 0
        if (i > n2) {
          if (нечетное(i))
            m3 = 1
          else
            m3 = -3
          j = (m1 + m3 + m2 * i) % N
        }
        MR(1)(i) = j
      }
      for(i <- 1 to N) print(MR(1)(i) + ",")
      SDVIG1(N, MR, 1, 1)
      for(i <- 1 to N) print(MR(2)(i) + ",")
      SDVIG1(N, MR, 2, 3)
      for(i <- 1 to N) print(MR(3)(i) + ",")
      SDVIG1(N, MR, 3, 2)
      for(i <- 1 to N) print(MR(4)(i) + ",")
      SDVIG1(N, MR, 4, 2)
      for(i <- 1 to N) print(MR(5)(i) + ",")
      SDVIG1(N, MR, 5, 4)
      for(i <- 1 to N) print(MR(6)(i) + ",")

    } else {

      val R2 = 1 // /количество решений/
      val m1 = 2
      val m2 = 2
      val n2 = N / 2
      val n1 = N - 1

      for(i <- 1 to n1) {
        var m3 = 0
        if (i > n2) {
          if (not(нечетное(i))) m3 = 1 else m3 = -3
        }
        MR(1)(i) = (m1 + m3 + m2 * i)
        if (MR(1)(i) > N) {
          MR(1)(i) = MR(1)(i) - N
        }
      }
      MR(1)(N) = 1
      //печать (1<=i<=n МR(1, i)),
      for(i <- 1 to N) print(MR(1)(i) + ",")

    }
  }
  
  def SDVIG1(N : Int, МR : Array[Array[Int]], Q : Int, SD : Int) : Unit = {

    // МR(Q, N) – массив решений,
    // q - счетчик решений,
    // SD – параметр сдвига:
    // 1 – сдвиг вправо (левая вертикаль доски становится правой вертикалью)
    // 2 - сдвиг влево  (правая вертикаль доски становится левой вертикалью)
    // 3 – сдвиг вниз   (верхняя горизонталь доски становится нижней горизонталью)
    // 4 – сдвиг вверх  (нижняя горизонталь доски становится верхней горизонталью)

      var q = Q + 1

      if (SD == 1) {
        for(i <- 1 to N) {
          МR (q)(i) = МR (q - 1)(i + 1)
          МR (q)(N) = МR (q - 1)(1)
        }
      }

      if (SD == 2) {
        for(i <- 1 to N) {
          МR (q)(i + 1) = МR (q - 1)(i)
          МR (q)(1) = МR (q - 1)(N)
        }
      }

      if (SD == 3) {
        for(i <- 1 to N) {
          МR(q)(i) = МR(q - 1)(i) + 1
          if (МR (q)(i) > N) {
            МR (q)(i) = 1
          }
        }
      }

      if (SD == 4) {
        for(i <- 1 to N) {
          МR (q)(i) = МR (q - 1)(i) - 1
          if (МR (q)(i) < 0) {
            МR (q)( i) = N
          }
        }
      }
    }
}
