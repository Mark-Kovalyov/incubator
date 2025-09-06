package mayton.chess.reporting;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class QueenReportWriter implements Closeable {

    protected PrintWriter printWriter;

    public QueenReportWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public abstract void writeEntity(QueenReportEntity reportEntity) throws IOException;

}
