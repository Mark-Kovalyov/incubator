package mayton
import java.io.PrintWriter

object MathPlotWriter extends HistWriter:
  override def write(w: PrintWriter, h: HistReport): Unit =
    w.print("OK")
  
