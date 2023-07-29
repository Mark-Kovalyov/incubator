package mayton.bigdata;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JSonIndentFormatter implements JSonFormatter{
    @Override
    public void format(Reader r, Writer w) throws IOException {
        int c;
        while((c = r.read()) > 0) {

            w.write(c);
        }
    }
}
