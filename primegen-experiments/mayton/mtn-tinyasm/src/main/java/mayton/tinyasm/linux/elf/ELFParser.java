package mayton.tinyasm.linux.elf;

import mayton.tinyasm.linux.elf.document.ELFDocument;
import mayton.tinyasm.linux.elf.document.ELFHeader;
import org.apache.commons.io.input.SwappedDataInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ELFParser {

    private static Logger logger = LoggerFactory.getLogger(ELFParser.class);

    private InputStream inputStream;

    public ELFParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    //
    public ELFDocument parse() throws IOException {
        ELFDocument elfDocument = new ELFDocument();
        ELFHeader elfHeader = new ELFHeader();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        //SwappedDataInputStream swappedDataInputStream = new SwappedDataInputStream(inputStream);
        elfHeader.magic = dataInputStream.readInt();
        logger.debug("magic = {}", elfHeader.magic);
        elfHeader.clazz = dataInputStream.readByte();
        logger.debug("class = {}", elfHeader.clazz);
        elfHeader.endian = dataInputStream.readByte();
        logger.debug("{}", elfHeader.toString());
        return elfDocument;
    }

}
