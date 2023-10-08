package mayton.elastic.http;

import java.io.Reader;
import java.util.Properties;

public interface ElasticSearchApi {

    void putIndex(String indexName, String propertiesJson);

    // Index API

    // Get API

    // Delete API

    // Update API
    void putDocument(String path, String jsonDocument) throws ElasticSearchException;

    void postDocument(String path, String jsonDocument) throws ElasticSearchException;

    void postDocument(String path, String jsonDocument,String version, String versionType) throws ElasticSearchException;

    /**
     * POST _bulk
     * { "index" : { "_index" : "test", "_id" : "1" } }
     * { "field1" : "value1" }
     * { "delete" : { "_index" : "test", "_id" : "2" } }
     * { "create" : { "_index" : "test", "_id" : "3" } }
     * { "field1" : "value3" }
     * { "update" : {"_id" : "1", "_index" : "test"} }
     * { "doc" : {"field2" : "value2"} }
     */
    void postBulk(Reader reader);

}
