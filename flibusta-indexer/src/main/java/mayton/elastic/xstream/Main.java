package mayton.elastic.xstream;

import com.thoughtworks.xstream.XStream;

import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        XStream xstream = new XStream();

        // does not require XPP3 library
        // XStream xstream2 = new XStream(new DomDriver());

        // does not require XPP3 library starting with Java 6
        //XStream xstream3 = new XStream(new StaxDriver());

        Person joe = new Person("Joe", "Walnes");
        joe.setPhone(new PhoneNumber(123, "1234-456"));
        joe.setFax(new PhoneNumber(123, "9999-999"));

        xstream.alias("person", Person.class);
        xstream.alias("phonenumber", PhoneNumber.class);

        String xml = xstream.toXML(joe);

        xstream.toXML(joe,new FileOutputStream("tmp/xstream/joe.xml"));

    }


}
