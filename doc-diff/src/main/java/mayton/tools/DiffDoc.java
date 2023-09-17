package mayton.tools;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiffDoc {

    static Logger logger = LoggerFactory.getLogger(DiffDoc.class);

    static final MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void processFile(File file, Collection<PathHash> pathHashes,int prefix, Pattern pattern) throws IOException {
        String absolutePath = file.getAbsolutePath();
        Matcher matcher = pattern.matcher(absolutePath);
        if (matcher.matches()) {
            md.reset();
            byte[] arr = IOUtils.toByteArray(Files.newInputStream(file.toPath()));
            md.update(arr);
            String sha1 = Hex.encodeHexString(md.digest());
            String localPath = file.getAbsolutePath().substring(prefix);
            logger.info("sha1 = {}, path = {}", sha1, localPath);
            pathHashes.add(new PathHash(localPath, sha1));
        }
    }

    public static void processFolder(File folder, Collection<PathHash> pathHashes, int prefix, Pattern pattern) throws IOException {
        for(File node : Objects.requireNonNull(folder.listFiles())) {
            if (node.isDirectory()) {
                processFolder(node, pathHashes,prefix, pattern);
            } else {
                processFile(node, pathHashes,prefix, pattern);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage: diff-doc [source-dir] [second-dir] [new-files-dir] [extensions]");
            System.exit(1);
        }
        Profiler profiler = new Profiler("diff-doc");
        String timeTag         = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm"));
        String sourceFirstDir  = args[0];
        String sourceSecondDir = args[1];
        String diffDir         = args[2];
        String extensions      = args[3];
        new File(diffDir).mkdirs();

        Pattern PATTERN = Pattern.compile(".+\\.(?<extension>" + extensions.replace(',','|') + ")$", Pattern.CASE_INSENSITIVE);

        profiler.start("Indexing first dir");
        logger.info("Indexing first dir");
        Set<PathHash> setSourceFirst = new HashSet<>();
        processFolder(new File(sourceFirstDir), setSourceFirst, sourceFirstDir.length(), PATTERN);
        logger.info("There are {} documents in first dir", setSourceFirst.size());

        profiler.start("Indexing second dir");
        logger.info("Indexing second dir");
        Set<PathHash> setSourceSecond = new HashSet<>();
        processFolder(new File(sourceSecondDir), setSourceSecond, sourceSecondDir.length(), PATTERN);
        logger.info("There are {} documents in second dir", setSourceSecond.size());

        profiler.start("Remove all");
        boolean removed = setSourceSecond.removeAll(setSourceFirst);
        logger.info("setSourceSecond size = {}", setSourceSecond.size());

        profiler.start("Saving result");

        PrintWriter pw = new PrintWriter(new FileWriter(diffDir + "/report-"+timeTag+".csv"));
        Iterator<PathHash> iter = setSourceSecond.iterator();
        while (iter.hasNext()) {
            PathHash ph = iter.next();
            pw.println(ph);
            String src  = sourceFirstDir + ph.path();
            String dest = diffDir + "/" + ph.hash();
            logger.info("copy from {} to NULL", src);
            IOUtils.copy(new FileInputStream(src), new NullOutputStream()); // TODO: Finish development
        }
        pw.close();

        logger.info("Finished");

        logger.info(profiler.stop().toString());


    }

}
