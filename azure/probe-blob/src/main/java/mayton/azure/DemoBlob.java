package mayton.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DemoBlob {

    public static void main(String[] args) throws FileNotFoundException {


        String connStr = "DefaultEndpointsProtocol=https;AccountName=mtnsa;AccountKey=********;EndpointSuffix=core.windows.net";

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connStr).buildClient();

        String containerName = "kitty";

        BlobContainerClient containerClient = null;

        try{
            containerClient = blobServiceClient.createBlobContainer(containerName);
        } catch (Exception ex) {
            containerClient = blobServiceClient.getBlobContainerClient(containerName);
        }

        BlobClient blobClient = containerClient.getBlobClient("cats/80365.jpg");
        File file = new File("cats/80365.jpg");
        blobClient.upload(new FileInputStream(file), file.length());

    }
}
