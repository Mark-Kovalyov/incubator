package mayton.bigdata;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

public class SetTier {

    static Logger logger = LoggerFactory.getLogger(SetTier.class);

    public static void main(String[] args) {

        if (args.length == 0) {
            logger.info("Use : java -jar azure-set-tier.jar [accountName] [accountKey] [containerName] {Cool | Archive | Premium | Cold}");
            return;
        }

        logger.info("Starting tier update process...");

        String accountName   = args[0];
        String accountKey    = args[1];
        String containerName = args[2];
        String tier          = args[3];

        Profiler profiler = new Profiler("SetTier");

        String connectionString = String.format(
                "DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                accountName, accountKey
        );

        profiler.start("Connecting");

        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        profiler.start("Getting container");

        BlobContainerClient container = serviceClient.getBlobContainerClient(containerName);

        profiler.start("Updating access tiers");

        int nfiles = 0;

        for (BlobItem blobItem : container.listBlobs()) {
            logger.info("Updating tier for blob: {}", blobItem.getName());
            BlobClient blobClient = container.getBlobClient(blobItem.getName());
            blobClient.setAccessTier(AccessTier.fromString(tier));
            nfiles++;
        }

        logger.info("There are {} files updated", nfiles);

        TimeInstrument ti = profiler.stop();

        logger.info(ti.toString());


    }


}
