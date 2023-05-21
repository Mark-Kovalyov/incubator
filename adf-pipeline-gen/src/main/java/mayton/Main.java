package mayton;

import mayton.adf.entities.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;
import java.io.*;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

public class Main {

    static Logger logger = LoggerFactory.getLogger("adf-pipeline-gen");

    public static void main(String[] args) throws ParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String notebookPrefix = "/home";

        AdfActivity notebook1 = new AdfActivity.Builder()
                .withName("name1")
                .withType(AdfActivityTypes.DatabricksNotebook)
                .withPolicy(new AdfPolicy("0.12:00:00", 0, 30, false, false))
                .withTypeProperties(
                        new AdfTypeProperties.Builder()
                                .withNotebookPath(notebookPrefix + "/script1")
                                .addLibrary(new AdfMavenLibrary("com.github.jsurfer:jsurfer-gson:1.6.3"))
                                .addLibrary(new AdfPyPiLibrary("aws","http://pypi.repo"))
                                .addBaseParameter("par1", "127.0.0.1")
                                .addBaseParameter("par2", 80)
                                .build()
                )
                .withLinkedService(
                        new AdfLinkedService(
                                "bigdata",
                                AdfLinkedServiceTypes.LinkedServiceReference,
                                new LinkedHashMap<>() {{
                                    put("DriverNodeType", "T1");
                                    put("WorkerNodeType", "T1");
                                    put("ClusterVersion", "9.1.x-scala2.12");
                                    put("MaxWorkerCount", 5);
                                }})
                        )
                .build();

        AdfActivity notebook2 = notebook1.cloneBuilder()
                .withName("name2")
                .withDependsOn(asList(
                        new AdfDependency("name1", asList(AdfDependencyCondition.Succeeded))))
                .build();

        AdfActivity notebook3 = notebook1.cloneBuilder()
                .withName("name3")
                .withDependsOn(asList(
                        new AdfDependency("name2", asList(AdfDependencyCondition.Succeeded))))
                .build();

        AdfPipeline pipeline = new AdfPipeline.Builder()
                .withName("Pipeline1")
                .withType("Microsoft.DataFactory/factories/pipelines")
                .addActivity(notebook1)
                .addActivity(notebook2)
                .addActivity(notebook3)
                .build();

        new File("out").mkdirs();

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                new FileWriter("out/adf-pipeline.json"), pipeline);


    }

}
