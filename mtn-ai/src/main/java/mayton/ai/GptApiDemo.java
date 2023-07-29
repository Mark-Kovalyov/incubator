package mayton.ai;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStore;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GptApiDemo {

    static Logger logger = LoggerFactory.getLogger("gpt-api-demo");

    public static List<String> models(HttpClient httpClient, String key) throws URISyntaxException, IOException, InterruptedException {

        List<String> res = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openai.com/v1/models"))
                .setHeader("Authorization", "Bearer " + key)
                //.setHeader("OpenAI-Organization", GPT_ORG_ID)
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

    public static void main(String[] args) throws Throwable {

        Uniconf uniconf = new Uniconf();

        String key = uniconf.lookupProperty("GPT_KEY").orElseThrow(() -> new Throwable("Cannot get key"));

        logger.info("GPT_KEY    = {}", key);

        System.out.printf("GPT_KEY       = %s\n", key);



        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();


        List<String> models = models(httpClient, key);


        for(String m : models.stream().sorted().collect(Collectors.toList())) {
            logger.info("{}",m);
        }


    }
}