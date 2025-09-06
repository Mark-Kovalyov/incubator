package ru.sql.cdc;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Master {

    String dburl;
    String dblogin;
    String dbpassword;

    String jmsurl;
    String jmsqueue;

    java.sql.Connection connection;

    static Logger logger = LogManager.getLogger(Master.class);

    public Options createOptions() {
        Options options = new Options();
        options.addOption("d", "dburl", true, "Master database url (ex:jdbc:postgresql://loclahost:5432/postgres)");
        options.addOption("u", "dbuser", true, "Master database user");
        options.addOption("p", "dbpassword", true, "Master database password");
        options.addOption("j", "jmsurl", true, "Message service Master url");
        options.addOption("q", "jmsqueue", true, "Message queue name (ex:CDC.QUEUE)");
        //options.addOption("s", "jmssessionmode", true, "Message session mode : {AUTO_ACKNOWLEDGE | CLIENT_ACKNOWLEDGE | DUPS_OK_ACKNOWLEDGE | SESSION_TRANSACTED}");
        options.addOption("s", "persistentdelivery", false, "PERSISTENT message delivery mode (boolean)");
        options.addOption("h", "help", false, "Print Help");
        return options;
    }

    private Iterable<EavEntity> selectRowEntities() throws SQLException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dburl, dblogin, dbpassword);
            } catch (SQLException ex) {
                logger.warn(ex);
                throw ex;
            }
        }
        List<EavEntity> rowEntities = new ArrayList<>();
        try(PreparedStatement pst = connection.prepareStatement("SELECT TS, OPERATION, TABLE_NAME, COLUMN_NAME, COL_VALUE FROM eav_log ORDER BY TS")) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                String ts = resultSet.getString(1);
                String operation = resultSet.getString(2);
                String tableName = resultSet.getString(3);
                String columnName = resultSet.getString(4);
                String columnValue = resultSet.getString(5);
                rowEntities.add(new EavEntity(ts, operation, tableName, columnName, columnValue));
            }
        }
        return rowEntities;
    }

    public void produce(String[] args) throws IOException, ParseException, JMSException, SQLException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(createOptions(), args);
        if (cmd.hasOption('h')) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar Master", createOptions() );
            return;
        }
        logger.info("Start producer");
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("master.properties"));

        dburl      = cmd.hasOption("d") ? cmd.getOptionValue("d") : properties.getProperty("dburl") ;
        dblogin    = cmd.hasOption("u") ? cmd.getOptionValue("u") : properties.getProperty("dblogin");
        dbpassword = cmd.hasOption("p") ? cmd.getOptionValue("p") : properties.getProperty("dbpassword");

        jmsurl     = cmd.hasOption("j") ? cmd.getOptionValue("j") : properties.getProperty("jmsurl");
        jmsqueue   = cmd.hasOption("q") ? cmd.getOptionValue("q") : properties.getProperty("jmsqueue");

        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(jmsurl);

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue(jmsqueue);

        // Create a MessageMaster from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(cmd.hasOption('s') ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);

        boolean isContinue = true;

        while(isContinue) {
            Iterator<JmsEntity> rowEntities = Utils.groupByTimeOperationTableName(selectRowEntities()).iterator();
            logger.info("Hearbeat...");
            while(rowEntities.hasNext()) {
                // Create a messages
                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                TextMessage message = session.createTextMessage(text);
                // Tell the producer to send the message
                logger.info("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);
            }
            sleep(30);
        }

        // Clean up
        session.close();
        connection.close();
        logger.info("Finished");
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws IOException, ParseException, JMSException, SQLException {
        new Master().produce(args);
    }

}
