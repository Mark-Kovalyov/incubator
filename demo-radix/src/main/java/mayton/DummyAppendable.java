package mayton;

import java.io.IOException;

public class DummyAppendable implements Appendable {

    public long lineCount = 0;
    public long charCount = 0;

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        if (csq.length() > 0 && csq.charAt(csq.length() -1) == '\n') {
            lineCount++;
        }
        charCount+= csq.length();
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Appendable append(char c) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
