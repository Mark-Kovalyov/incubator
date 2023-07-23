package mayton.ai;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import mayton.lib.Uniconf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

public class GptApiDemo {

    static Logger logger = LoggerFactory.getLogger("gpt-api-demo");

    static String GPT_KEY    = System.getenv("GPT_KEY");
    static String GPT_ORG_ID = System.getenv("GPT_ORG_ID");



    public static List<String> models(HttpClient httpClient) throws URISyntaxException, IOException, InterruptedException {

        List<String> res = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openai.com/v1/models"))
                .setHeader("Authorization", "Bearer " + GPT_KEY)
                .setHeader("OpenAI-Organization", GPT_ORG_ID)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String body = response.body();
            //logger.info("{}", body);
            DocumentContext jsonContext = JsonPath.parse(body);
            res = jsonContext.read("$['data'].[*].['id']");
            logger.info("{}", response.body());
        } else {
            logger.error("Error {}", response.statusCode());
            logger.error("{}", response.body());
        }
        return res;
    }

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

        Uniconf uniconf = new Uniconf("app.properties");

        logger.info("GPT_KEY    = {}", uniconf.lookupProperty("GPT_KEY").orElseThrow());
        logger.info("GPT_ORG_ID = {}", uniconf.lookupProperty("GPT_ORG_ID").orElseThrow());

        System.out.printf("GPT_KEY       = %s\n", uniconf.lookupProperty("GPT_KEY").orElseThrow());
        System.out.printf("GPT_ORG_ID    = %s\n", uniconf.lookupProperty("GPT_ORG_ID").orElseThrow());



        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();


        List<String> models = models(httpClient);


        for(String m : models.stream().sorted().collect(Collectors.toList())) {
            logger.info("{}",m);
        }


    }
}