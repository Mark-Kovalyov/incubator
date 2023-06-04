package mayton.sound;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Encode {

    public static Logger logger = LoggerFactory.getLogger(Encode.class);

    public static Options createOptions() {
        return new Options()
                .addRequiredOption("s", "source", true, "Source file")
                .addOption("d", "destination", true, "Destination file");
    }

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();
        if (args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar ApplicationName-1.0.jar", createOptions(), true);
        } else {
            CommandLine line = parser.parse(options, args);
            process(line);
        }
    }

    private static void process(CommandLine line) throws FileNotFoundException {
        logger.info("Start");
        try(InputStream is = new FileInputStream(line.getOptionValue("s"));
            OutputStream os = new FileOutputStream(line.getOptionValue("d"))) {
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Finish");
    }
}
