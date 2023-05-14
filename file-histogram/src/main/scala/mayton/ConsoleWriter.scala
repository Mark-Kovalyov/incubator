package mayton

import java.io.PrintWriter

object ConsoleWriter extends HistWriter:

  def fmt(d : Double) = java.lang.String.format("%.02f", d)

  override def write(w: PrintWriter, h: HistReport): Unit =
    w.println(s"${h.tag} as of ${h.ts}")
    w.println()
    w.println(s"hash = ${h.hash}")
    w.println()
    w.println("╭─────────┬───────┬───────────────┬────┬────╮")
    w.println("│Extension│Files  │Cumulative size│ %  │Rank│")
    w.println("┝─────────┼───────┼───────────────┼────┼────┤")
    val sum = h.rows.map(x => x.count).sum
    val loc = new java.util.Locale("en", "EN")
    val formatter = java.text.NumberFormat.getIntegerInstance(loc)
    h.rows
      .sorted
      .foreach(row => {

        val extPad         = row.ext.padTo(9,' ')
        val rowCountPad    = formatter.format(row.count).padTo(7, ' ')
        val rowFileSizePad = formatter.format(row.fileSize).padTo(15,' ')
        val cumSizePad     = fmt(row.count.toDouble / sum).padTo(4, ' ')
        val extRankPad     = row.extRank.toString.padTo(4, ' ')
        w.println(s"│${extPad}│${rowCountPad}│${rowFileSizePad}│${cumSizePad}│${extRankPad}│")
      })
    w.println("╰─────────┴───────┴───────────────┴────┴────╯")
