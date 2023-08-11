package mayton.azure;

import com.azure.cosmos.*;
import com.azure.cosmos.models.*;

public class DemoCosmosDb {

    public static void main(String[] args) {

        CosmosClientBuilder clientBuilder = new CosmosClientBuilder();

        CosmosClient cosmosClient = clientBuilder
                .endpoint("https://......azure.com:443")
                .key("*********************")
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();

        String dbName = "scott";

        CosmosContainerProperties ccp = new CosmosContainerProperties("emp", "/id");
        CosmosDatabase cosmosDb = cosmosClient.getDatabase(dbName);
        CosmosContainerResponse res = cosmosDb.createContainerIfNotExists(ccp);

        res.getStatusCode();

        cosmosClient.close();

    }

}
