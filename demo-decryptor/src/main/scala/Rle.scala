package mayton.crypto

object Rle{

  def repeatChar(char:Char, n: Int) = char.toString() * n

  def decode1(rle : String): Either[String,String] =
    val result = StringBuilder()
    val dsb = StringBuilder()
    var c: Char = '\u0000'
    val opts : List[Option[Char]] = rle.toCharArray.toList.map(Some(_))
    // a = A8B1C4X5.
    // b = .A8B1C4X5
    val pairs = (opts ++ List(None)).zip(List(None) ++ opts)
    for
      pair <- pairs
    do
      pair match
        case (Some(a), None) => c = a
        case (Some(a),Some(b)) if a.isLetter && b.isDigit  =>
          result.append(repeatChar(c, dsb.toString().toInt))
          dsb.clear()
          c = a
        case (Some(a),Some(b)) if a.isDigit => dsb.append(a)
        case (None, Some(b)) => result.append(repeatChar(c, dsb.toString().toInt))
        case _ => {}
    Right(result.toString())


  def encode1(text: String): String = {
    val sb = StringBuilder()
    var cnt = 0
    var prev: Char = '\u0000'
    var curr: Char = '\u0000'
    for(i <- 0 until text.length) {
      curr = text(i)
      if (prev == curr) {
        cnt += 1
      } else {
        if (prev != Char.MaxValue) {
          sb.append(prev)
          sb.append(cnt)
        }
        cnt = 1
      }
      prev = curr
    }
    if (cnt > 0) {
      sb.append(prev)
      sb.append(cnt)
    }
    sb.toString()
  }


  def randomAlphabetic(n : Int): String = org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(n)

  def infseq_lazylist2(n : Int) : LazyList[String] = randomAlphabetic(n) #:: infseq_lazylist2(n)

  def main(args: Array[String]): Unit ={
    for(sample <- List("AAAAAAAABCCCCXXXXX", "A", ""))
      println(s"Text = '${sample}' , RLE encoded = '${Rle.encode1(sample)}'")

    println(Rle.decode1("AB3").getOrElse("Error"))

  }

}

