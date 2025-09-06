package mayton.primes

import mayton.primes.PrimeLib._
import mayton.primes.Stohastic.P_bernully

object Main {

  def main(args : Array[String]) : Unit = {

    val p = 0.66666666666

    printf(" |")
    for(n <- 1 to 10) {
      printf("%d |".format(n))
    }
    printf("\n")

    for(k <- 1 to 10) {
      printf("%d |".format(k))
      for(n <- 1 to 10) {
        if (k <= n) {
          val b = P_bernully(k, n, p)
          printf("%f | ".format(b))
        } else {
          printf("%f | ".format(0.0))
        }
      }
      printf(s"\n")
    }
  }

}
