package mayton.kafka;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class DemoBuilder {

    static Logger logger = LoggerFactory.getLogger("demo");

    public static void main(String[] args) throws IOException {

        Schema emp = SchemaBuilder.record("emp")
                .namespace("com.oracle.scott")
                .fields()
                    .requiredString("empid")
                    .optionalString("opt_ename")
                    .requiredDouble("sal")
                    .optionalDouble("comm")
                .endRecord();

        Schema avroHttpRequest = SchemaBuilder.record("AvroHttpRequest")
                .namespace("com.oracle.scott")
                .fields()
                .requiredLong("requestTime")
                .name("clientIdentifier")
                    .type(emp)
                    .noDefault()
                .name("employeeNames")
                    .type()
                    .array()
                        .items()
                        .stringType()
                        .arrayDefault(new ArrayList<>())
                .name("active")
                    .type()
                    .enumeration("Active")
                    .symbols("YES","NO")
                    .noDefault()
                .endRecord();

        new File("out").mkdirs();
        Writer writer = new FileWriter("out/avroHttpRequest.avsc");
        writer.write(avroHttpRequest.toString());
        writer.close();

        Writer writer2 = new FileWriter("out/emp.avsc");
        writer2.write(emp.toString());
        writer2.close();
    }

}
