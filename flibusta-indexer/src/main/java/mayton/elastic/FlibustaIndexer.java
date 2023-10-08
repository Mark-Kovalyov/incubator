package mayton.elastic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.function.Function;
import mayton.elastic.converters.XmlToFb2DomConverter;
import mayton.elastic.converters.XmlToFb2StaxConverter;
import mayton.elastic.http.ElasticSearchApi;
import mayton.elastic.http.ElasticSearchApiImpl;
import mayton.elastic.http.ElasticSearchException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// TODO: Refactor!
public class FlibustaIndexer {

    static Logger logger = LoggerFactory.getLogger(FlibustaIndexer.class);

    public static void pushFb2Document(Fb2Document fb2Document) {
        ElasticSearchApi elasticSearchClient = ElasticSearchApiImpl.createInstance();
        String path = fb2Document.getPath();
        try {
            // TODO:
            elasticSearchClient.putDocument(path, "");
        } catch (ElasticSearchException e) {
            logger.error(":: error ", e);
        }
    }

    public static void processFlibustaFb2Document(InputStream fb2Stream) {
        XmlToFb2StaxConverter xmlToFb2DocumentConverter = new XmlToFb2StaxConverter();
        Fb2Document doc = xmlToFb2DocumentConverter.apply(fb2Stream);
        logger.info(":: doc = {}", doc.toString());
    }

    public static void processFlibustaZipArchive(ZipInputStream zipInputStream, String fileName) throws IOException {

        logger.info(":: Processing archive {}", fileName);

        ZipEntry entry = zipInputStream.getNextEntry();
        while(entry != null) {
            String entryName = entry.getName();
            if (entryName.endsWith(".fb2")) {
                logger.info(":: Processing FB2 zip entry {}", entryName);
                logger.info(":: Compression method {}", entry.getMethod());
                logger.info(":: Size {}", entry.getSize());
                logger.info(":: Compression size {}", entry.getCompressedSize());
                    // TODO: This is fucken bull-shit! But I have no idea how-to control InputStream::close in XMLEventReader
                    // processFlibustaFb2Document(zipInputStream)
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    IOUtils.copy(zipInputStream, out);
                    processFlibustaFb2Document(new ByteArrayInputStream(out.toByteArray()));

            }
            entry = zipInputStream.getNextEntry();
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }


    public static void prepareIndex() {
        logger.info(":: prepareIndex");
        ElasticSearchApi elasticSearchApi = ElasticSearchApiImpl.createInstance();
        elasticSearchApi.putIndex("flibusta", null);
    }

    public static void processFiles() throws IOException {
        logger.info(":: process files");

        File dir = new File("/storage/torrent/fb2.Flibusta.Net");

        for(File file : dir.listFiles()) {
            if (file.isDirectory()) continue;
            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".zip")) {
                ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
                processFlibustaZipArchive(zipInputStream, fileName);
                zipInputStream.close();
            }
        }


    }

    public static void main(String[] args) throws Exception {

        logger.info(":: Start");


        logger.info(":: Finish");
    }

}
