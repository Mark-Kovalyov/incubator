package mayton.elastic.http;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import mayton.elastic.FlibustaIndexer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Refactor!
public class ElasticSearchApiImpl implements ElasticSearchApi, Indexable  {

    private String indexName;

    static Logger logger = LoggerFactory.getLogger(FlibustaIndexer.class);

    private static ElasticSearchApiImpl elasticSearchClient = null;

    private HttpClient client;

    public synchronized static ElasticSearchApi createInstance() {
        if (elasticSearchClient == null) {
            elasticSearchClient = new ElasticSearchApiImpl();
        }
        return elasticSearchClient;
    }

    private ElasticSearchApiImpl() {
        client = HttpClientBuilder.create().build();
    }

    @Override
    public void putIndex(String indexName, String propertiesJson) {
        final String url = "https://127.0.0.1:9200/"+indexName;
        HttpPut put = new HttpPut(url);

    }

    @Override
    public void putDocument(String path, String jsonDocument) throws ElasticSearchException {

        final String url = "https://127.0.0.1:9200/"+indexName+"/" + path;

        HttpPut put = new HttpPut(url);

        try {
            put.setEntity(new StringEntity(jsonDocument));
            HttpResponse response = client.execute(put);
            logger.info("Response Code : {}" , response.getStatusLine().getStatusCode());
            InputStream is = response.getEntity().getContent();
            if (logger.isDebugEnabled()) {
                String result = IOUtils.toString(is, UTF_8);
                logger.debug("{}", result);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            throw new ElasticSearchException("UnsupportedEncodingException");
        } catch (ClientProtocolException e) {
            logger.error("", e);
            throw new ElasticSearchException("ClientProtocolException");
        } catch (IOException e) {
            logger.error("", e);
            throw new ElasticSearchException("IOException");
        }

    }

    @Override
    public void postDocument(String path, String jsonDocument) throws ElasticSearchException {

    }

    @Override
    public void postDocument(String path, String jsonDocument, String version, String versionType) throws
                                                                                                   ElasticSearchException {

    }

    @Override
    public void postBulk(Reader reader) {

    }

    @Override
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
