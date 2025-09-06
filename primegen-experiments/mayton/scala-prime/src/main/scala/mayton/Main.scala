package mayton

import java.math.BigInteger

object Main {

  def bigPower(x : Int, y : Int) : BigInt = {
    var product : BigInt = 1
    for(k <- 1 to y) {
      product = product * x
    }
    product
  }

  def main(arg : Array[String]) : Unit = {
    val x : BigInt = bigPower(2, 1023)
    var javaBigint = new BigInteger(x.toString(10))
    javaBigint = javaBigint.nextProbablePrime()
    do {
      javaBigint = javaBigint.nextProbablePrime()
      println(javaBigint)
      println(" ")
    } while(javaBigint.compareTo( x + BigInt(2000)) < 0) // while(javaBigInt < x + 2000)

  }

}
