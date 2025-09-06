package mayton.chess.reporting;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;

public class GithubQueenReportWriter extends QueenReportWriter {

    public GithubQueenReportWriter(PrintWriter printWriter) {
        super(printWriter);
        printWriter.println("Quenns(%)|Queens|Cells Under Fire(CUF)|Desk Space(DS)|CUF/DS ratio");
        printWriter.println("-|-|-|-|-");
    }

    @Override
    public void writeEntity(QueenReportEntity reportEntity) throws IOException {
        printWriter.print(reportEntity.queens);
        printWriter.print("|");
        printWriter.print(reportEntity.queensPercent);
        printWriter.print("|");
        printWriter.print(reportEntity.queens);
        printWriter.print("|");
        printWriter.print(reportEntity.CUF);
        printWriter.print("|");
        printWriter.print(reportEntity.DS);
        printWriter.print("|");
        printWriter.print(reportEntity.CUFDSratio);
        printWriter.println();
    }

    @Override
    public void close() throws IOException {
        printWriter.close();
    }
}
