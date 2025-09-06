object Main {

  type pairAnyVal = (AnyVal,AnyVal)

  def parseArgNumbers(argNumbers: String): List[AnyVal] = argNumbers.split(",").map(s => s.toInt).toList

  def parseTuple(s : String) : pairAnyVal = {
    val sp = s.indexOf(",")
    (s.substring(0, sp).toInt, s.substring(sp + 1).toInt)
  }

  def parseExclude(argExclude: String): List[(AnyVal,AnyVal)] = argExclude.split(";").map(s => parseTuple(s)).toList

  def containsTuple(subNumbers: List[AnyVal], pair: pairAnyVal): Boolean = {
    subNumbers.contains(pair._1) && subNumbers.contains(pair._2)
  }

  def contains(subNumbers: List[AnyVal], excluded: List[pairAnyVal]): Boolean = {
    excluded.filter(pair => containsTuple(subNumbers,pair)).take(1).size == 1
  }

  def trickyCombinations(numbers: List[AnyVal], excludes: List[pairAnyVal]): Unit = {
    val size = numbers.size
    for(i <- 1 to size) {
      printf(s"The combinations $i of $size\n")
      numbers.combinations(i).foreach(f => {
        if (!contains(f,excludes)) {
          for (v <- f) printf("%s,", v)
          printf("\n")
        }
      })
      printf("\n\n")
    }

  }

  def main(args : Array[String]) : Unit = {

    printf("\nNumbers : ")
    val argNumbers = scala.io.StdIn.readLine()

    printf("\nExclude : ")
    val argExclude = scala.io.StdIn.readLine()

    println()

    val numbers = parseArgNumbers(argNumbers)
    val excludes = parseExclude(argExclude)

    trickyCombinations(numbers,excludes)

  }

}
