package mayton.crypto

class Decryptor(encrypted:String) {

  val encryptedSet : Set[Char] = encrypted.toCharArray.toSet

  val base64chars   = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=" // = for padding
  val base58bitcoin = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
  val base58ripple  = "rpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2bcdeCg65jkm8oFqi1tuvAxyz"
  val base58flickr  = "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"
  val base32chars   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567=" // '=' for padding
  // TODO:
  val base32geohash = ""
  val binhex        = ""

  def collectHistogram() : Map[Char,Int] = {
    val mmap = collection.mutable.Map[Char,Int]()
    for(c <- encrypted) {
      if (mmap.contains(c))
        mmap(c) += 1
      else
        mmap(c) = 1
    }
    mmap.toMap
  }

  def isBase32(): Boolean = {
    val serviceChars: Set[Char] = List('\n', '\r', '\n').toSet
    encryptedSet.diff(serviceChars).subsetOf(base32chars.toCharArray.toSet)
  }

  def isBase64(): Boolean = {
    val serviceChars: Set[Char] = List('\n', '\r', '\n').toSet
    encryptedSet.diff(serviceChars).subsetOf(base64chars.toCharArray.toSet)
  }
}

object Decryptor{
  def main(args:Array[String]) = {
    val encrypted =
      """
        |
        |""".stripMargin

    val res = new Decryptor(encrypted)

    println(s"The test is base64? ${res.isBase64()}")
    println(s"The test is base64? ${res.isBase32()}")

    println("b64 - b64 = " + (res.encryptedSet.diff(res.encryptedSet)))

    println("e - b64 = " + (encrypted.toCharArray.toSet diff res.base64chars.toArray.toSet))

    println("b64 - e = " + (res.base64chars.toArray.toSet diff encrypted.toCharArray.toSet))

  }
}
