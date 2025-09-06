package mayton.chess.reporting;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class QueenReportEntity {

    public final String queensPercent;
    public final String queens;
    public final String CUF;
    public final String DS;
    public final String CUFDSratio;

    public QueenReportEntity(String queensPercent, String queens, String cuf, String square, String cufdSratio) {
        this.queensPercent = queensPercent;
        this.queens = queens;
        CUF = cuf;
        this.DS = square;
        CUFDSratio = cufdSratio;
    }
}
