package ru.sql.cdc;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.cli.Options;

import javax.jms.*;

public class Slave {

    public Options createOptions() {
        Options options = new Options();
        options.addOption("d", "dburl", true, "Slave database url (ex:jdbc:postgresql://loclahost:5432/postgres)");
        options.addOption("u", "dbuser", true, "Slave database user");
        options.addOption("p", "dbpassword", true, "Slave database password");
        options.addOption("j", "jmsurl", true, "Message service Slave url");
        options.addOption("q", "jmsqueue", true, "Message queue name (ex:CDC.QUEUE)");
        return options;
    }


    public static void main(String[] args) throws JMSException {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // connection.setExceptionListener(this);

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("TEST.FOO");

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);

        // Wait for a message
        Message message = consumer.receive(1000);

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("Received: " + text);
        } else {
            System.out.println("Received: " + message);
        }

        consumer.close();
        session.close();
        connection.close();
    }

}
