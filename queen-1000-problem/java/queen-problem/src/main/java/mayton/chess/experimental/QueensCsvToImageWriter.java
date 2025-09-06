package mayton.chess.experimental;

import mayton.chess.datastructures.RestrictedClassicQueensDesk;
import mayton.chess.datastructures.RestrictedQueensDesk;
import mayton.chess.tools.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class QueensCsvToImageWriter {

    static Logger logger = LoggerFactory.getLogger(QueensCsvToImageWriter.class);


    public static void main(String[] args) throws Exception {

        String csvFile   = args[0];
        String imageFile = args[1];
        int n = Integer.parseInt(args[2]);

        logger.info("Processing csv file {} with size {}", csvFile, n);

        RestrictedQueensDesk iqd = new RestrictedClassicQueensDesk(n);

        String res = FileUtils.readFileToString(new File(csvFile), StandardCharsets.UTF_8);
        String[] positions = StringUtils.split(res,",\n ");

        int x = 0;
        int y = 0;

        // TODO: Test
        for(String position : positions) {
            if (!position.isEmpty()) {
                x = Integer.parseInt(position);
                if (!iqd.tryToSetQueen(x, y)) throw new Exception("Aborted! Unable to set queen!");
                y++;
            }
        }

        ImageUtils.writeImage(new FileOutputStream(imageFile),iqd);

        logger.info("OK");
    }

}
