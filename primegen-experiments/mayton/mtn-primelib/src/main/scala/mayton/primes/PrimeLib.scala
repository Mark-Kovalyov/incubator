package mayton.primes

import java.lang.Math.log
import java.lang.Math.abs
import java.lang.Math.pow
import java.math.BigInteger

import scala.annotation.tailrec

object PrimeLib {

  val π = Math.PI

  val e = Math.E

  val Χcrypto = BigInt(2).pow(4096)

  val Χmersenne = BigInt(2).pow(82589933) - 1

  implicit class IntToBase(val digits: String) extends AnyVal {
    def b = BigInt(digits)
    def h = BigInt(digits,16)
    def bi = BigInt(digits,2)
  }

  def estimatePrimes(x: Double) : Double = x / log(x)

  /**
    * Ferma't number
    *
    * @param x
    * @return
    */
  def F(x: Int) : BigInt = {
    val pow2x : BigInt = BigInt(2).pow(x)
    BigInt(2).pow(pow2x.toInt)
  }

  /**
    * Euler's function
    *
    * <pre>
    *   φ(0) =
    *
    * </pre>
    *
    * @param n
    * @return
    */
  def φ(n: Long): Long = {
    var result = 1
    for(i <- 2L to n)
      if (gcd(i,n) == 1)
        result = result + 1

    result
  }

  def φ(n: Int): Int = {
    var result = 1
    for(i <- 2 to n)
      if (gcd(i,n) == 1)
        result = result + 1

    result
  }

  def fermaTest(a: BigInt, n:BigInt): Boolean = {
    // TODO:
    ???
  }

  def lukasLemerRizel(n: BigInt): Boolean = {

    def magnitude(x: BigInt):Int = {
      // TODO: Should be designed like Java's "int u = n.mag[n.mag.length-1];"
      0
    }

    def jacobiSymbol(pp: Int, n: BigInt) : Int = {
      var p = pp
      if (p == 0) return 0

      // Algorithm and comments adapted from Colin Plumb's C library.
      var j = 1
      var u = magnitude(n)

      // Make p positive
      if (p < 0) {
        p = -p
        val n8 = u & 7
        if ((n8 == 0x03) || (n8 == 0x07)) j = -j // 3 (011) or 7 (111) mod 8
      }

      // Get rid of factors of 2 in p
      while ( {
        (p & 3) == 0
      }) p >>= 2
      if ((p & 1) == 0) {
        p >>= 1
        if (((u ^ (u >> 1)) & 2) != 0) {
          j = -j // 3 (011) or 5 (101) mod 8
        }
      }
      if (p == 1) return j
      // Then, apply quadratic reciprocity
      if ((p & u & 2) != 0) { // p = u = 3 (mod 4)?
        j = -j
      }
      // And reduce u mod p
      u = n.mod(BigInteger.valueOf(p)).intValue

      // Now compute Jacobi(u,p), u < p
      while ( u != 0 ) {
        while ((u & 3) == 0) {
          u >>= 2
        }
        if ((u & 1) == 0) {
          u >>= 1
          if (((p ^ (p >> 1)) & 2) != 0) {
            j = -j
          }
        }
        if (u == 1) return j
        // Now both u and p are odd, so use quadratic reciprocity
        assert(u < p)
        val t = u
        u = p
        p = t
        if ((u & p & 2) != 0) { // u = p = 3 (mod 4)?
          j = -j
        }
        // Now u >= p, so it can be reduced
        u %= p
      }
      0
    }

    def lucasLehmerSequence(z: Int, k: BigInt, n: BigInt) : BigInt = {

      def shiftRight(n : BigInt, bits : Int) : BigInt = {
        ???
      }

      def sq(n : BigInt) : BigInt = {
        ???
      }

      def testBit(n : BigInt, bit : Int) : Boolean = {
        false
      }

      val d = z
      var u = BigInt(1)
      var u2 = BigInt(0)
      var v = BigInt(1)
      var v2 = BigInt(0)

      var i = k.bitLength - 2
      while (i >= 0) {
        //u2 = u.*(v).%(n)
        u2 = ( u * v ) % n
        //v2 = sq(v).+(d.*(sq(u))).mod(n)
        v2 = sq(v) + (( d * sq(u)) % n)
        if (v2.testBit(0)) {
          v2 = v2 - n
        }
        v2 = shiftRight(v2, 1)
        u = u2
        v = v2
        if (k.testBit(i)) {
          u2 = u.+(v).%(n)
          if (testBit(u2,0)) {
            u2 = u2 - n
          }
          u2 = shiftRight(u2,1)
          //v2 = v.+(d.*(u)).%(n)
          v2 = v + ( d * u ) % n
          if (v2.testBit(0)) {
            v2 = v2 - n
          }
          v2 = shiftRight(v2,1)
          u = u2
          v = v2
        }
        i -= 1;
        i = i + 1
      }
      u
    }

    var d = 5

    while (jacobiSymbol(d, n) != -1) {
      // 5, -7, 9, -11, ...
      d = if (d < 0) abs(d) + 2 else -(d + 2)
    }

    // Step 2
    val u = lucasLehmerSequence(d, n + 1, n)

    // Step 3
    u % n == 0
  }

  /**
    * Prime candidates generator
    *
    * @return
    */
  def primeCandidates() : Stream[Int] = {
    def primeCandidates(n : Int, step : Int) : Stream[Int] = {
      n #:: primeCandidates(n + step, step^0x6)
    }

    2 #:: 3 #:: primeCandidates(5, 2)
  }

  /**
    * Factorial
    *
    * @param x
    * @return
    */
  def fact(x: BigInt): BigInt = {
    if (x < 0)
      throw new IllegalArgumentException

    @tailrec def facttail(x: BigInt, accum: BigInt): BigInt =
      if (x == 0)
        accum
      else
        facttail(x - 1, accum * x)

    facttail(x, 1)
  }

  def prod(a: BigInt, b: BigInt): BigInt = {
    if (a == b) return a
    var tx = BigInt(1)
    for (i <- a to b) {
      tx = tx * i
    }
    tx
  }

  /**
    * Сочетания. Так-же известны как биноминальный коэффициент
    *
    * \binom{n}{k} = \frac{n!}{k!*(n-k)!}
    *
    * @param n
    * @return
    */
  def combinations(k: BigInt, n: BigInt): BigInt = {
    if (k > n || n < 1 || k < 1) throw new IllegalArgumentException
    if (k == n) return BigInt(1)
    // TODO: Refactor. Optimize
    fact(n) / ( fact(k) * fact(n - k) )
  }

  /**
    * Перестановки.
    * A(m,n) = \frac{n!}{(n-k)!}
    * @param k
    * @param n
    * @return
    */
  def permutations(k: BigInt, n: BigInt): BigInt = {
    if (k > n || n < 1 || k < 1) throw new IllegalArgumentException

    val ch = fact(n)
    val zn = fact(n - k)

    ch / zn
  }

  /**
    * Наибольший общий делитель
    *
    * @param a
    * @param b
    * @return
    */
  def gcd(a: BigInt, b: BigInt): BigInt = if (b != BigInt(0)) gcd(b, a % b) else a

  def gcd(a: Long, b: Long): Long = if (b != 0) gcd(b, a % b) else a

  def gcd(a: Int, b: Int): Int = if (b != 0) gcd(b, a % b) else a

  //def gcd[T] (a : T, b : T) : T = if (b.equals(0)) gcd(b, a.to[T] % b) else a

  //def gcd(a: AnyVal, b: AnyVal): AnyVal = if (b != 0) gcd(b, a % b) else a

  /**
    * Наименьшее общее кратное
    *
    * @param a
    * @param b
    * @return
    */
  def lcm(a: BigInt, b: BigInt): BigInt = (a * b) / gcd(a, b)

  /**
    * Два числа взаимно простые
    *
    * @param a
    * @param b
    * @return
    */
  def mutuallyPrime(a: BigInt, b: BigInt): Boolean = gcd(a,b) == BigInt(1)

  def mutuallyPrime(a: Long, b: Long): Boolean = gcd(a,b) == 1

  def mutuallyPrime(a: Int, b: Int): Boolean = gcd(a,b) == 1

  //def mutuallyPrime[T](a: T, b: T) : Boolean = gcd(a, b) == 1

  def mutuallySimple(list : List[BigInt]) : Boolean = {
    if (list.length < 2) throw new IllegalArgumentException
    BigInt(1) == list.reduce((a,b) => gcd(a,b))
  }


}
