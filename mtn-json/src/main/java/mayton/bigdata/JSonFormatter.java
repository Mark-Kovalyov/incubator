package mayton.bigdata;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface JSonFormatter {

    void format(Reader r, Writer w) throws IOException;

}
