package mayton.generators.demo;

import org.apache.commons.cli.*;

import java.io.*;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

public class JavaJdbcApplication {

    static Logger logger = LoggerFactory.getLogger("${appName}");

    public static Options buildOptions() {
        Options options = new Options();
        options.addRequiredOption("j", "jdbc-url", true, "JDBC url");
        options.addRequiredOption("q", "query", true, "SQL query");
        return options;
    }

    static CommandLine parseCommandLine(String[] args) throws ParseException {
        return new DefaultParser().parse(buildOptions(), args);
    }

    public static void process(Connection conn, CommandLine cli) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(cli.getOptionValue("q"));
        long cnt = 0;
        while(res.next()) {
            cnt++;
        }
        logger.info("count = {}", cnt);
        res.close();
    }

    public static void main(String[] args) throws ParseException, IOException, SQLException {
        if (args.length == 0) {
            new HelpFormatter().printHelp("java -jar ${appName}.jar", buildOptions());
            System.exit(1);
        } else {
            CommandLine cli = parseCommandLine(args);
            Connection conn = DriverManager.getConnection(cli.getOptionValue("j"));
            Profiler profiler = new Profiler("app-name");
            profiler.start("query");
            process(conn, cli);
            TimeInstrument profilerReport = profiler.stop();
            logger.info("{}", profilerReport.toString());
            conn.close();
        }
    }

}
