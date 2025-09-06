package mayton.chess.reporting;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.format;

public class ExcelQueenReportWriter extends QueenReportWriter {

    private CSVPrinter excelPrinter;

    public ExcelQueenReportWriter(PrintWriter printWriter) throws IOException {
        super(printWriter);
        excelPrinter = new CSVPrinter(
                printWriter,
                CSVFormat.EXCEL.
                        withHeader(
                                "Quenns(%)",
                                "Queens",
                                "Cells Under Fire(CUF)",
                                "Desk Space(DS)",
                                "CUF/DS ratio"));
    }

    @Override
    public void writeEntity(QueenReportEntity reportEntity) throws IOException {
        excelPrinter.printRecord(
                reportEntity.queensPercent,
                reportEntity.queens,
                reportEntity.CUF,
                reportEntity.DS,
                reportEntity.CUFDSratio);
    }

    @Override
    public void close() throws IOException {
        excelPrinter.close();
    }
}
