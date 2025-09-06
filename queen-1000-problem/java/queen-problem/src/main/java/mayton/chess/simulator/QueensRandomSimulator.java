package mayton.chess.simulator;

import mayton.chess.reporting.ExcelQueenReportWriter;
import mayton.chess.reporting.GithubQueenReportWriter;
import mayton.chess.reporting.QueenReportEntity;
import mayton.chess.reporting.QueenReportWriter;
import mayton.chess.tools.ImageUtils;
import mayton.chess.datastructures.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static mayton.chess.Constants.IMAGES_ROOT;
import static mayton.chess.Constants.REPORTS_ROOT;

public class QueensRandomSimulator {

    private static final int REPEAT_TRIES = 2000;

    static Logger logger = LoggerFactory.getLogger(QueensRandomSimulator.class);

    static Random r = new Random(System.currentTimeMillis());

    public static boolean drawDesk(
                                RestrictedQueensDeskFactory factory,
                                List<QueenReportWriter> queenReportWriters,
                                int queensPercent,
                                int queensGap,
                                String filename,
                                String technology) throws Exception {

        RestrictedQueensDesk iqd = factory.getRestrictedQueensDesk();

        int size = iqd.getSize();

        int falseResults = 0;
        int queens = 0;
        boolean prevRes = false;
        int i=0;
        int cellsUnderFire = 0;

        boolean breakByOverflow = false;

        while(!breakByOverflow) {
            boolean res = iqd.tryToSetQueen(r.nextInt(size), r.nextInt(size));
            if (res) {
                queens++;
            }
            if (queens > queensGap) {
                break;
            }
            if (!res && !prevRes) {
                falseResults++;
            } else {
                falseResults=0;
            }
            prevRes = res;
            if (i>0) {
                if (falseResults > REPEAT_TRIES) {
                    logger.warn("Abandoned after {} repeating tries on {} queens for {} tecnhology stack",
                            REPEAT_TRIES,
                            queens,
                            technology);
                    breakByOverflow = true;
                    break;
                }
            }
            i++;
        }

        if (!breakByOverflow) {

            Path path = Paths.get(IMAGES_ROOT + "/" + technology);
            Files.createDirectories(path);

            ImageUtils.writeImage(new FileOutputStream(IMAGES_ROOT + "/" + technology + "/" + filename + ".png"), iqd);

            cellsUnderFire = calculateCellsUnderFire(iqd, size, cellsUnderFire);

            int square = size * size;

            for(QueenReportWriter queenReportWriter : queenReportWriters) {
                queenReportWriter.writeEntity(
                        new QueenReportEntity(
                                valueOf(queensPercent),
                                valueOf(queens),
                                valueOf(cellsUnderFire),
                                valueOf(square),
                                valueOf((double) cellsUnderFire / (double) square)));
            }

        }

        return breakByOverflow;
    }

    private static int calculateCellsUnderFire(RestrictedQueensDesk iqd, int size, int cellsUnderFire) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (iqd.getValue(x, y) || iqd.isUnderFire(x, y)) {
                    cellsUnderFire++;
                }
            }
        }
        return cellsUnderFire;
    }

    public static void main(String[] args) throws Exception {

        int SIZE = 250;

        simulateCustomRestrictedDesk("torus", 100, 1, SIZE,
                new RestrictedTorusQueensDeskFactory(SIZE));

        simulateCustomRestrictedDesk("classic", 100, 1, SIZE,
                new RestrictedClassicQueensDeskFactory(SIZE));


    }

    private static void simulateCustomRestrictedDesk(
            String technologyStack, int percentGep, int step, int size,
            RestrictedQueensDeskFactory factory) throws Exception {

        Path path = Paths.get(REPORTS_ROOT + "/"+technologyStack);
        Files.createDirectories(path);

        try(QueenReportWriter qrwEx = new ExcelQueenReportWriter(
                    new PrintWriter(
                        format(REPORTS_ROOT + "/%s/%s-density-report-%dx%d.csv",
                        technologyStack,
                        technologyStack,
                        size,
                        size)));

            /*
            QueenReportWriter qrwHub = new GithubQueenReportWriter(
                    new PrintWriter(
                        format(REPORTS_ROOT + "/" + technologyStack + "/README.md" )))*/
                        ) {

            for (int i = 0; i <= percentGep; i += step) {
                boolean breakByOverflow = drawDesk(
                        factory,
                        asList(qrwEx),
                        i,
                        (i * size) / 100,
                        format(technologyStack + "desk-%dx%d-with-%03d-percent-quens-filling",
                                size,
                                size,
                                i
                        ),
                        technologyStack
                );

                if (breakByOverflow) break;
            }
        }

    }

}
