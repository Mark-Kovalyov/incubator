package mayton

import java.io.PrintWriter

trait HistWriter:
  def write(w:PrintWriter, h:HistReport) : Unit

