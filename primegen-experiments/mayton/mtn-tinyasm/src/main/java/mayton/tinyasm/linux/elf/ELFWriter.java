package mayton.tinyasm.linux.elf;

import mayton.tinyasm.linux.elf.document.ELFDocument;

import javax.annotation.Nonnull;
import java.io.OutputStream;

public class ELFWriter implements AutoCloseable {

    private OutputStream outputStream;

    public ELFWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(@Nonnull ELFDocument elfDocument) {


    }

    @Override
    public void close() throws Exception {
        outputStream.close();
    }
}
