package mayton.tinyasm;


import mayton.tinyasm.linux.elf.ELFParser;
import mayton.tinyasm.linux.elf.document.ELFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.regex.Pattern;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void processLibrary(Path path) throws IOException {
        InputStream is = new FileInputStream(
                path.getParent().toString() + "/" + path.getFileName().toString());

        ELFParser elfParser = new ELFParser(is);
        ELFDocument res = elfParser.parse();

    }

    static Pattern pattern = Pattern.compile(".+\\.(so|axf|bin|o|prx|exe)$", Pattern.CASE_INSENSITIVE);


    public static void main(String[] args) throws IOException {

        Path start = Path.of("/home/mayton/cpp");
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String name = file.getFileName().toString();
                String parent = file.getParent().toString();
                if (pattern.matcher(name).matches()) {
                    logger.info("{} {}", parent, name);
                    processLibrary(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }

        });


    }
}
