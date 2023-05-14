package mayton

object Main {

  def main(arg : Array[String]) : Unit = {

    val fontCol = List(
      ConsoleColors.ANSI_BLACK,
      ConsoleColors.ANSI_RED,
      ConsoleColors.ANSI_GREEN,
      ConsoleColors.ANSI_YELLOW,
      ConsoleColors.ANSI_BLUE,
      ConsoleColors.ANSI_PURPLE,
      ConsoleColors.ANSI_CYAN,
      ConsoleColors.ANSI_WHITE
    )

    val backCol = List(
      ConsoleColors.ANSI_BLACK_BACKGROUND, // 0,0,0
      ConsoleColors.ANSI_RED_BACKGROUND,   // 197, 15, 31
      ConsoleColors.ANSI_YELLOW_BACKGROUND,// 193, 156, 0
      ConsoleColors.ANSI_CYAN_BACKGROUND,  // 58, 150, 221
      ConsoleColors.ANSI_BLUE_BACKGROUND,  // 0, 55, 218
      ConsoleColors.ANSI_GREEN_BACKGROUND, // 0, 255, 0
      ConsoleColors.ANSI_PURPLE_BACKGROUND,// 136, 23, 152
      ConsoleColors.ANSI_WHITE_BACKGROUND  // 204,204,204
    )

    for {
       col  <- fontCol
       bcol <- backCol
    } {
      val s = " ░▒▓█"
      print(col)
      print(bcol)
      print(s)
      print(ConsoleColors.ANSI_RESET)
      if (bcol == ConsoleColors.ANSI_WHITE_BACKGROUND) println()
    }

  }
  
}
