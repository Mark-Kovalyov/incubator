package mayton

import java.io.PrintWriter

object ColoredConsoleWriter extends HistWriter:
    def fmt(d: Double) = java.lang.String.format("%.02f", d)

    override def write(w: PrintWriter, h: HistReport): Unit =
      w.println(s"${h.tag} as of ${h.ts}")
      w.println()
      w.println(s"hash = ${h.hash}")
      w.println()

      w.print(ConsoleColors.ANSI_RED_BACKGROUND + ConsoleColors.ANSI_WHITE)
      w.print(" Extension│Files  │Cumulative size│ %  │Rank ")
      w.print(ConsoleColors.ANSI_RESET)
      w.println()
      val sum = h.rows.map(x => x.count).sum
      val loc = new java.util.Locale("en", "EN")
      val formatter = java.text.NumberFormat.getIntegerInstance(loc)
      var cnt = 0
      h.rows
        .sorted
        .foreach(row => {
          cnt = cnt + 1
          w.print(ConsoleColors.ANSI_BLACK + ConsoleColors.ANSI_WHITE_BACKGROUND)
          val extPad = row.ext.padTo(9, ' ')
          val rowCountPad = formatter.format(row.count).padTo(7, ' ')
          val rowFileSizePad = formatter.format(row.fileSize).padTo(15, ' ')
          val cumSizePad = fmt(row.count.toDouble / sum).padTo(4, ' ')
          val extRankPad = row.extRank.toString.padTo(4, ' ')
          w.print(s" ${extPad}│${rowCountPad}│${rowFileSizePad}│${cumSizePad}│${extRankPad} ")
          w.print(ConsoleColors.ANSI_RESET)
          w.println()
        })


