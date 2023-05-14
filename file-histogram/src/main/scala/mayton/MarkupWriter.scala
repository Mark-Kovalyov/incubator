package mayton
import java.io.PrintWriter

object MarkupWriter extends HistWriter:

  def fmt(d : Double) = java.lang.String.format("%.02f", d)

  override def write(w: PrintWriter, h: HistReport): Unit = 
    w.println(s"# ${h.tag} as of ${h.ts}")
    w.println()
    w.println(s"* hash = ${h.hash}")
    w.println()
    w.println("|Extension|Files|Cumulative size| %  |Rank|")
    w.println("|---------|-----|---------------|----|----|")
    val sum = h.rows.map(x => x.count).sum
    h.rows
      .sorted
      .foreach(row =>
        w.println(s"|${row.ext}|${row.count}|${row.fileSize}|${fmt(row.count.toDouble / sum)}|${row.extRank}")
      )

  
